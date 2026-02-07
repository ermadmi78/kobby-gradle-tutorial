package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.program.code

import graphql.schema.DataFetchingEnvironment
import graphql.schema.FieldCoordinates.coordinates
import graphql.schema.GraphQLCodeRegistry
import graphql.schema.PropertyDataFetcher.fetching
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.FilmData
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.FilmResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher.FilmActorsFetcher
import kotlin.coroutines.CoroutineContext

/**
 * Created on 22.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object FilmCode {
    fun register(
        builder: GraphQLCodeRegistry.Builder,
        resolver: FilmResolutionModel,
        aspect: ResolutionAspect,
        coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
    ) {
        builder
            .dataFetcher(
                coordinates("Film", "id"),
                fetching<Long, FilmData> { it.id }
            )
            .dataFetcher(
                coordinates("Film", "title"),
                fetching<String, FilmData> { it.title }
            )
            .dataFetcher(
                coordinates("Film", "actors"),
                FilmActorsFetcher(resolver, aspect, coroutineContextProvider)
            )
    }
}