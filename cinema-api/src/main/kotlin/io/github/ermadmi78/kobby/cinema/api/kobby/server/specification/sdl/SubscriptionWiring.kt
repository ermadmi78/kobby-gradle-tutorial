package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.sdl

import graphql.schema.DataFetchingEnvironment
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.SubscriptionResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher.SubscriptionFilmCreatedFetcher
import kotlin.coroutines.CoroutineContext

/**
 * Created on 22.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object SubscriptionWiring {
    fun register(
        builder: RuntimeWiring.Builder,
        resolver: SubscriptionResolutionModel,
        aspect: ResolutionAspect,
        coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
    ) {
        builder.type(
            newTypeWiring("Subscription")
                .dataFetcher(
                    "filmCreated",
                    SubscriptionFilmCreatedFetcher(resolver, aspect, coroutineContextProvider)
                )
        )
    }
}