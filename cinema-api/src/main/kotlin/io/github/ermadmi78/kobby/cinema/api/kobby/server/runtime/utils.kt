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

suspend fun resolutionEnvironment(): DataFetchingEnvironment =
    currentCoroutineContext()[ResolutionContext.Key]?.environment
        ?: error("Cinema resolution context is not configured")

val DEFAULT_CONTEXT_PROVIDER: (DataFetchingEnvironment) -> CoroutineContext = {
    Dispatchers.Default + ResolutionContext(it)
}

val DEFAULT_RESOLUTION_ASPECT: ResolutionAspect = object : ResolutionAspect {
    override suspend fun <T> around(environment: DataFetchingEnvironment, resolution: suspend () -> T): T =
        resolution()
}