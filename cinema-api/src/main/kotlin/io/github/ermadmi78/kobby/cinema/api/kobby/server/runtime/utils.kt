package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime

import graphql.schema.DataFetchingEnvironment
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.CinemaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlin.coroutines.CoroutineContext

/**
 * Created on 16.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */

suspend fun cinemaResolutionEnvironment(): CinemaResolutionEnvironment =
    currentCoroutineContext()[CinemaResolutionEnvironment.Key]
        ?: error("Cinema resolution environment is not configured")

val DEFAULT_CINEMA_CONTEXT_PROVIDER: (DataFetchingEnvironment) -> CoroutineContext = {
    Dispatchers.Default + CinemaResolutionEnvironment(it)
}

val DEFAULT_CINEMA_RESOLUTION_ASPECT: CinemaResolutionAspect = object : CinemaResolutionAspect {
    override suspend fun beforeResolving(dataFetchingEnvironment: DataFetchingEnvironment) {
        // Do nothing
    }

    override suspend fun afterResolving(
        data: CinemaData?,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) {
        // Do nothing
    }
}