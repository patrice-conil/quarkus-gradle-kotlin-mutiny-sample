package conil.patrice

import io.smallrye.mutiny.Uni
import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeoutException
import java.util.logging.Logger
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DeviceStatusService {
    private val logger: Logger = Logger.getLogger(this.javaClass.canonicalName)

    data class UniContext(val emitter: CompletableFuture<String>)

    //Todo: Change for redis registration with TTL
    val notificationMap = mutableMapOf<String, UniContext>()

    fun getStatus(requestStatus: RequestStatus): Uni<String> {

        // Call async service here ... it will call us back on notifications endpoint
        // It will just reply ... your request is accepted

        val emitter = CompletableFuture<String>()
        notificationMap[requestStatus.device] = UniContext(emitter)
        return Uni.createFrom().completionStage(emitter.minimalCompletionStage())
            .ifNoItem().after(Duration.ofSeconds(60))
            .failWith {
                cleanContexts(requestStatus.device)
                TimeoutException("No reply for ${requestStatus.device}")
            }
    }

    fun cleanContexts(id: String) {
        notificationMap.remove(id)
    }

    fun receive(notification: Notification) {
        logger.fine("Notification received for ${notification.device}")
        notificationMap[notification.device]?.let {
            it.emitter.complete(notification.status)
            notificationMap.remove(notification.device)
        }
    }

}
