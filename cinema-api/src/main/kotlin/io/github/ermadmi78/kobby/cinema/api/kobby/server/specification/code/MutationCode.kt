package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.code

import graphql.schema.DataFetchingEnvironment
import graphql.schema.FieldCoordinates.coordinates
import graphql.schema.GraphQLCodeRegistry
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.CinemaMutationResolver
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher.MutationCreateFilmFetcher
import kotlin.coroutines.CoroutineContext

/**
 * Created on 15.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object MutationCode {
    fun register(
        builder: GraphQLCodeRegistry.Builder,
        resolver: CinemaMutationResolver,
        coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
    ) {
        builder
            .dataFetcher(
                coordinates("Mutation", "createFilm"),
                MutationCreateFilmFetcher(resolver, coroutineContextProvider)
            )
    }
}