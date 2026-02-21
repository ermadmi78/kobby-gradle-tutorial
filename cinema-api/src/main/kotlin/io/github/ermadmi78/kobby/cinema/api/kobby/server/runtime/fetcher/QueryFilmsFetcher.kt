package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.FilmData
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.QueryResolutionModel
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
class QueryFilmsFetcher(
    private val resolver: QueryResolutionModel,
    private val coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
) : DataFetcher<CompletableFuture<List<FilmData>>> {
    override fun get(environment: DataFetchingEnvironment): CompletableFuture<List<FilmData>> {
        val context = coroutineContextProvider(environment)
        return CoroutineScope(context).async {
            resolver.films()
        }.asCompletableFuture()
    }
}