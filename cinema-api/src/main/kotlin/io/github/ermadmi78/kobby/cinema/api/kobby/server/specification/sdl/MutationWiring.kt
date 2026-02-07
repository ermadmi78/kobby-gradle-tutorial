package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.sdl

import graphql.schema.DataFetchingEnvironment
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.MutationResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher.MutationCreateFilmFetcher
import kotlin.coroutines.CoroutineContext

/**
 * Created on 22.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object MutationWiring {
    fun register(
        builder: RuntimeWiring.Builder,
        resolver: MutationResolutionModel,
        aspect: ResolutionAspect,
        coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
    ) {
        builder.type(
            newTypeWiring("Mutation")
                .dataFetcher(
                    "createFilm",
                    MutationCreateFilmFetcher(resolver, aspect, coroutineContextProvider)
                )
        )
    }
}