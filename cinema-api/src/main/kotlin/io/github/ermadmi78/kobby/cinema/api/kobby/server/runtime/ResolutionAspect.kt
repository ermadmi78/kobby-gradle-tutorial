package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime

import graphql.schema.DataFetchingEnvironment

/**
 * Created on 21.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
interface ResolutionAspect {
    suspend fun <T> around(environment: DataFetchingEnvironment, resolution: suspend () -> T): T
}