package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime

import graphql.schema.DataFetchingEnvironment
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * Created on 16.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class CinemaEnvironmentContext(
    val environment: DataFetchingEnvironment
) : AbstractCoroutineContextElement(CinemaEnvironmentContext) {
    companion object Key : CoroutineContext.Key<CinemaEnvironmentContext>
}