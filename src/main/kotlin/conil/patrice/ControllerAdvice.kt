package conil.patrice

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import org.jboss.resteasy.reactive.RestResponse
import org.jboss.resteasy.reactive.server.ServerExceptionMapper
import java.util.concurrent.TimeoutException
import javax.validation.ConstraintViolationException
import javax.ws.rs.core.Response

@Suppress("unused")
class ControllerAdvice(val objectMapper: ObjectMapper) {

    data class ErrorInfo(val code: String, val message: String?)

    @Suppress("unused")
    @ServerExceptionMapper
    fun mapException(e: ValueInstantiationException): RestResponse<String> {
        val error = ErrorInfo(
            Response.Status.BAD_REQUEST.statusCode.toString(),
            e.originalMessage
        )
        return RestResponse.status(
            Response.Status.BAD_REQUEST,
            objectMapper.writeValueAsString(error)
        )
    }

    @Suppress("unused")
    @ServerExceptionMapper
    fun mapException(e: ConstraintViolationException): RestResponse<String> {
        val error =
            ErrorInfo(
                Response.Status.BAD_REQUEST.statusCode.toString(),
                e.constraintViolations.joinToString(", ", "[", "]") { v -> "${v.propertyPath}: ${v.message}" }
            )
        return RestResponse.status(
            Response.Status.BAD_REQUEST,
            objectMapper.writeValueAsString(error)
        )
    }

    @Suppress("unused")
    @ServerExceptionMapper
    fun mapException(e: TimeoutException): RestResponse<String> {
        val error = ErrorInfo(
                Response.Status.BAD_REQUEST.statusCode.toString(),
                e.message
            )
        return RestResponse.status(
            Response.Status.SERVICE_UNAVAILABLE,
            objectMapper.writeValueAsString(error)
        )
    }
}
