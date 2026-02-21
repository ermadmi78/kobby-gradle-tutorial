package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime

import graphql.schema.DataFetchingEnvironment
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.CinemaData

/**
 * Created on 21.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
interface CinemaResolutionAspect {
    suspend fun beforeResolving(
        dataFetchingEnvironment: DataFetchingEnvironment
    )

    suspend fun afterResolving(
        data: CinemaData?,
        dataFetchingEnvironment: DataFetchingEnvironment
    )
}