@file:Suppress("UNCHECKED_CAST")

package io.github.ermadmi78.kobby.cinema.api.kobby.server

import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLCodeRegistry
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLSchema
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.FilmResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.MutationResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.QueryResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.SubscriptionResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.DEFAULT_CONTEXT_PROVIDER
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.DEFAULT_RESOLUTION_ASPECT
import io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.program.code.*
import io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.program.type.*
import kotlin.coroutines.CoroutineContext

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */

fun buildCinemaSchemaUsingProgram(
    scalarLong: GraphQLScalarType,
    queryResolver: QueryResolutionModel,
    mutationResolver: MutationResolutionModel,
    subscriptionResolver: SubscriptionResolutionModel,
    filmResolver: FilmResolutionModel,
    aspect: ResolutionAspect = DEFAULT_RESOLUTION_ASPECT,
    coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext = DEFAULT_CONTEXT_PROVIDER
): GraphQLSchema {
    val codeRegistry: GraphQLCodeRegistry.Builder = GraphQLCodeRegistry.newCodeRegistry()
    QueryCode.register(codeRegistry, queryResolver, aspect, coroutineContextProvider)
    MutationCode.register(codeRegistry, mutationResolver, aspect, coroutineContextProvider)
    SubscriptionCode.register(codeRegistry, subscriptionResolver, aspect, coroutineContextProvider)
    FilmCode.register(codeRegistry, filmResolver, aspect, coroutineContextProvider)
    ActorCode.register(codeRegistry)

    return GraphQLSchema.newSchema()
        .additionalType(scalarLong)
        .query(QueryType.build())
        .mutation(MutationType.build())
        .subscription(SubscriptionType.build())
        .additionalType(FilmType.build())
        .additionalType(ActorType.build())
        .codeRegistry(codeRegistry.build())
        .build()
}