@file:Suppress("UNCHECKED_CAST")

package io.github.ermadmi78.kobby.cinema.api.kobby.server

import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLCodeRegistry
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLSchema
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.CinemaFilmResolver
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.CinemaMutationResolver
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.CinemaQueryResolver
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.CinemaSubscriptionResolver
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.DEFAULT_CINEMA_CONTEXT_PROVIDER
import io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.CinemaScalar
import io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.code.MutationCode
import io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.code.QueryCode
import io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.types.MutationType
import io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.types.QueryType
import kotlin.coroutines.CoroutineContext

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */

fun buildCinemaSchema(
    queryResolver: CinemaQueryResolver,
    mutationResolver: CinemaMutationResolver,
    subscriptionResolver: CinemaSubscriptionResolver,
    filmResolver: CinemaFilmResolver,
    scalarID: GraphQLScalarType = CinemaScalar.GraphQLID,
    scalarString: GraphQLScalarType = CinemaScalar.GraphQLString,
    coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext = DEFAULT_CINEMA_CONTEXT_PROVIDER
): GraphQLSchema {
    val codeRegistry: GraphQLCodeRegistry.Builder = GraphQLCodeRegistry.newCodeRegistry()
    QueryCode.register(codeRegistry, queryResolver, coroutineContextProvider)
    MutationCode.register(codeRegistry, mutationResolver, coroutineContextProvider)

    return GraphQLSchema.newSchema()
        .query(QueryType.build(scalarID))
        .mutation(MutationType.build(scalarString))
        .codeRegistry(codeRegistry.build())
        .build()
}