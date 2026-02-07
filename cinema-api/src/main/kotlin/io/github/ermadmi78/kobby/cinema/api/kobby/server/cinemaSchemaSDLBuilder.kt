package io.github.ermadmi78.kobby.cinema.api.kobby.server

import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLScalarType
import graphql.schema.idl.RuntimeWiring
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.FilmResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.MutationResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.QueryResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.SubscriptionResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.DEFAULT_CONTEXT_PROVIDER
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.DEFAULT_RESOLUTION_ASPECT
import io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.sdl.*
import kotlin.coroutines.CoroutineContext

/**
 * Created on 15.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */

fun wireCinemaSchemaRuntime(
    runtimeWiring: RuntimeWiring.Builder,
    scalarLong: GraphQLScalarType,
    queryResolver: QueryResolutionModel,
    mutationResolver: MutationResolutionModel,
    subscriptionResolver: SubscriptionResolutionModel,
    filmResolver: FilmResolutionModel,
    aspect: ResolutionAspect = DEFAULT_RESOLUTION_ASPECT,
    coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext = DEFAULT_CONTEXT_PROVIDER
) {
    runtimeWiring
        .scalar(scalarLong)

    QueryWiring.register(runtimeWiring, queryResolver, aspect, coroutineContextProvider)
    MutationWiring.register(runtimeWiring, mutationResolver, aspect, coroutineContextProvider)
    SubscriptionWiring.register(runtimeWiring, subscriptionResolver, aspect, coroutineContextProvider)
    FilmWiring.register(runtimeWiring, filmResolver, aspect, coroutineContextProvider)
    ActorWiring.register(runtimeWiring)
}