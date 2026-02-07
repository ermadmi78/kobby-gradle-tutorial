package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.program.code

import graphql.schema.DataFetchingEnvironment
import graphql.schema.FieldCoordinates.coordinates
import graphql.schema.GraphQLCodeRegistry
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.QueryResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher.QueryFilmFetcher
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher.QueryFilmsFetcher
import kotlin.coroutines.CoroutineContext

/**
 * Created on 15.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object QueryCode {
    fun register(
        builder: GraphQLCodeRegistry.Builder,
        resolver: QueryResolutionModel,
        aspect: ResolutionAspect,
        coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
    ) {
        builder
            .dataFetcher(
                coordinates("Query", "film"),
                QueryFilmFetcher(resolver, aspect, coroutineContextProvider)
            )
            .dataFetcher(
                coordinates("Query", "films"),
                QueryFilmsFetcher(resolver, aspect, coroutineContextProvider)
            )
    }
}