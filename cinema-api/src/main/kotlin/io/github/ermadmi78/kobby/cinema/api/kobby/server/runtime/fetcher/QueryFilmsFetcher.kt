package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.fetcher

import graphql.GraphQLError
import graphql.execution.DataFetcherResult
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.FilmData
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.QueryResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionEnvironment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
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
    private val aspect: ResolutionAspect,
    private val coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext
) : DataFetcher<CompletableFuture<DataFetcherResult<List<FilmData>>>> {
    override fun get(environment: DataFetchingEnvironment): CompletableFuture<DataFetcherResult<List<FilmData>>> {
        val context = coroutineContextProvider(environment)
        return CoroutineScope(context).async {
            val data = aspect.around(environment) {
                resolver.films()
            }

            val env: ResolutionEnvironment? = currentCoroutineContext()[ResolutionEnvironment.Key]
            DataFetcherResult.newResult<List<FilmData>>()
                .data(data)
                .errors(env?.extractGraphQLErrors() ?: emptyList<GraphQLError>())
                .extensions(env?.extractGraphQLExtensions())
                .build()
        }.asCompletableFuture()
    }
}