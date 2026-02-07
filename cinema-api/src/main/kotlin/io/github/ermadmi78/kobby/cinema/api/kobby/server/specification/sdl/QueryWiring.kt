package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.sdl

import graphql.schema.DataFetchingEnvironment
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.QueryResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher.QueryFilmFetcher
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher.QueryFilmsFetcher
import kotlin.coroutines.CoroutineContext

/**
 * Created on 22.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object QueryWiring {
    fun register(
        builder: RuntimeWiring.Builder,
        resolver: QueryResolutionModel,
        aspect: ResolutionAspect,
        coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
    ) {
        builder.type(
            newTypeWiring("Query")
                .dataFetcher(
                    "film",
                    QueryFilmFetcher(resolver, aspect, coroutineContextProvider)
                )
                .dataFetcher(
                    "films",
                    QueryFilmsFetcher(resolver, aspect, coroutineContextProvider)
                )
        )
    }
}