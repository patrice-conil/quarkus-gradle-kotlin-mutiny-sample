package conil.patrice


import io.smallrye.mutiny.Uni
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/status")
class DeviceStatusController(
    private val deviceStatusService: DeviceStatusService
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getStatus(@Valid @NotNull requestStatus: RequestStatus): Uni<String> {
        return deviceStatusService.getStatus(requestStatus)
    }
}
