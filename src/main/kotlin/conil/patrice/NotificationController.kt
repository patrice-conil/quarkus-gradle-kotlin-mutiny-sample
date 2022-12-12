package conil.patrice

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import javax.enterprise.context.ApplicationScoped
import javax.validation.Valid
import javax.ws.rs.POST
import javax.ws.rs.Path

@ApplicationScoped
@Path("/notifications")
class NotificationController(val service: DeviceStatusService) {

    @POST
    fun receive(@RequestBody @Valid notification: Notification) {
        service.receive(notification)
    }
}