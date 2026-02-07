package io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.runtime.wiring

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.data.FilmData
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.runtime.CinemaSubscriptionResolver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import kotlinx.coroutines.reactive.asPublisher
import org.reactivestreams.Publisher
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext

/**
 * Created on 08.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class SubscriptionFilmCreatedFetcher(
    private val resolver: CinemaSubscriptionResolver,
    private val coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
) : DataFetcher<CompletableFuture<Publisher<FilmData>>> {
    override fun get(environment: DataFetchingEnvironment): CompletableFuture<Publisher<FilmData>> {
        val context = coroutineContextProvider(environment)
        return CoroutineScope(context).async {
            resolver.filmCreated().asPublisher(context)
        }.asCompletableFuture()
    }
}
