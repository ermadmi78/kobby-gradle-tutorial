package io.github.ermadmi78.kobby.cinema.server.serverless

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.cancellation.CancellationException

/**
 * Created on 09.03.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class CinemaServerlessSubscriber<T> : Subscriber<T> {
    private val mutex = Mutex()
    private val subscriptionHolder = CompletableDeferred<Subscription>()
    private val valueHolderRef = AtomicReference<CompletableDeferred<T>>(CompletableDeferred())

    suspend fun awaitSubscription() {
        subscriptionHolder.await()
    }

    suspend fun awaitNext(): T = mutex.withLock {
        val subscription = subscriptionHolder.await()
        val valueHolder = valueHolderRef.get()
        if (valueHolder.isActive) {
            subscription.request(1L)
        }

        return@withLock valueHolder.await()
    }

    suspend fun awaitCancel() {
        val subscription = subscriptionHolder.await()
        subscription.cancel()
    }

    override fun onSubscribe(subscription: Subscription) {
        subscriptionHolder.complete(subscription)
    }

    override fun onNext(value: T) {
        valueHolderRef.getAndSet(CompletableDeferred()).complete(value)
    }

    override fun onError(t: Throwable) {
        subscriptionHolder.completeExceptionally(t)
        valueHolderRef.getAndSet(
            CompletableDeferred<T>().also {
                it.completeExceptionally(t)
            }
        ).completeExceptionally(t)
    }

    override fun onComplete() {
        onError(CancellationException("Subscription finished"))
    }
}