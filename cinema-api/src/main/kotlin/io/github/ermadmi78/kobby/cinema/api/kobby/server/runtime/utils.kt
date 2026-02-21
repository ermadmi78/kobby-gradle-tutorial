package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime

import graphql.schema.DataFetchingEnvironment
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