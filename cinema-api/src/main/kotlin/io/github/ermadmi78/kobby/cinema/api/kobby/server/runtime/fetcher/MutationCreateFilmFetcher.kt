package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.FilmData
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.MutationResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext

/**
 * Created on 08.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class MutationCreateFilmFetcher(
    private val resolver: MutationResolutionModel,
    private val aspect: ResolutionAspect,
    private val coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
) : DataFetcher<CompletableFuture<FilmData>> {
    override fun get(environment: DataFetchingEnvironment): CompletableFuture<FilmData> {
        val context = coroutineContextProvider(environment)
        return CoroutineScope(context).async {
            val argTitle: String = environment.getArgument("title") ?: error("Cannot find argument: argTitle")

            aspect.around(environment) {
                resolver.createFilm(argTitle)
            }
        }.asCompletableFuture()
    }
}