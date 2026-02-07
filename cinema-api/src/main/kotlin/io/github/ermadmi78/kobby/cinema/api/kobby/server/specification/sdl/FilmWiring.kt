package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.sdl

import graphql.schema.DataFetchingEnvironment
import graphql.schema.PropertyDataFetcher.fetching
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
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
internal object FilmWiring {
    fun register(
        builder: RuntimeWiring.Builder,
        resolver: FilmResolutionModel,
        aspect: ResolutionAspect,
        coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
    ) {
        builder.type(
            newTypeWiring("Film")
                .dataFetcher(
                    "id",
                    fetching<Long, FilmData> { it.id }
                )
                .dataFetcher(
                    "title",
                    fetching<String, FilmData> { it.title }
                )
                .dataFetcher(
                    "actors",
                    FilmActorsFetcher(resolver, aspect, coroutineContextProvider)
                )
        )
    }
}