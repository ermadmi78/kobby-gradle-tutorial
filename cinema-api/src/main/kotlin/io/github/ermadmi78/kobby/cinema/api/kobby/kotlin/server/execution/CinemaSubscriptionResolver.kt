package io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.execution

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.data.FilmData
import kotlinx.coroutines.flow.Flow

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
interface CinemaSubscriptionResolver {
    suspend fun filmCreated(): Flow<FilmData>
}