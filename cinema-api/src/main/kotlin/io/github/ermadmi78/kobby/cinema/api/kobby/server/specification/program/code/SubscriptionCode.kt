package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.program.code

import graphql.schema.DataFetchingEnvironment
import graphql.schema.FieldCoordinates.coordinates
import graphql.schema.GraphQLCodeRegistry
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.SubscriptionResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher.SubscriptionFilmCreatedFetcher
import kotlin.coroutines.CoroutineContext

/**
 * Created on 22.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object SubscriptionCode {
    fun register(
        builder: GraphQLCodeRegistry.Builder,
        resolver: SubscriptionResolutionModel,
        aspect: ResolutionAspect,
        coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
    ) {
        builder
            .dataFetcher(
                coordinates("Subscription", "filmCreated"),
                SubscriptionFilmCreatedFetcher(resolver, aspect, coroutineContextProvider)
            )
    }
}