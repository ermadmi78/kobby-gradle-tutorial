package io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.execution

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.data.FilmData

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
interface CinemaQueryResolver {
    suspend fun film(id: Long): FilmData?

    suspend fun films(): List<FilmData>
}