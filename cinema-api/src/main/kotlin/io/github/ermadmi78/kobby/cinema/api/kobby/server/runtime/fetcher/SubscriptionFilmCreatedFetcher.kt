package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.FilmData
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.SubscriptionResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asPublisher
import org.reactivestreams.Publisher
import kotlin.coroutines.CoroutineContext

/**
 * Created on 08.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class SubscriptionFilmCreatedFetcher(
    private val resolver: SubscriptionResolutionModel,
    private val aspect: ResolutionAspect,
    private val coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
) : DataFetcher<Publisher<FilmData>> {
    override fun get(environment: DataFetchingEnvironment) = Publisher { subscriber ->
        val context = coroutineContextProvider(environment)
        CoroutineScope(context).launch {
            aspect.around(environment) {
                resolver.filmCreated()
            }.asPublisher(context).subscribe(subscriber)
        }
    }
}
