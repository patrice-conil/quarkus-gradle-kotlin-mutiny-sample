package conil.patrice

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture

internal class UniTest {
    @Test
    fun uniFromCompletableFuture() {
        val future = CompletableFuture<Int>()
        val uni = Uni.createFrom().completionStage(future.minimalCompletionStage())
        val subscriber: UniAssertSubscriber<Int> = uni
            .subscribe().withSubscriber(UniAssertSubscriber.create())
        println("do something then complete")
        future.complete(2)
        subscriber.assertCompleted().assertItem(2)
    }

    @Test
    fun uniThatFailsFromCompletableFuture() {
        val future = CompletableFuture<Int>()
        val uni = Uni.createFrom().completionStage(future.minimalCompletionStage())
        val subscriber = uni.subscribe().withSubscriber(UniAssertSubscriber.create())
        println("do something then complete with Error")
        future.completeExceptionally(RuntimeException("failed"))
        subscriber.assertFailedWith(RuntimeException::class.java, "failed")
    }

    @Test
    fun multi() {
        val multi = Multi.createFrom().range(1, 100)
        multi.map { it.toString() }
        multi.subscribe().with(
            { item -> println(item) },
            { failure -> failure.printStackTrace() })
    }

    @Test
    fun multi2() {
        val multi = Multi.createFrom().range(1, 100)
        multi.map { it.toString() }
        multi
            .onItem().invoke { item -> println(item) }
            .onFailure().invoke { failure -> failure.printStackTrace() }
    }


    @Test
    fun multiFromPublisher() {
        var counter = 0
        val multi = Multi.createFrom().range(1, 10)
        val multi2 = multi.map { it + 1 }
        val multi3 = Multi.createBy().merging().streams(
            multi,
            multi2
        )
        multi3.map {
            it * 2
        }
            .subscribe()
            .with(
                { println(it) },
                { err -> err.printStackTrace() }
            )
    }
}