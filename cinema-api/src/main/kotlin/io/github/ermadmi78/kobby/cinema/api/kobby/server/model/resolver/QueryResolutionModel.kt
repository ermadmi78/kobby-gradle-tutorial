package io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver

import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.FilmData

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
interface QueryResolutionModel {
    suspend fun film(id: Long): FilmData?

    suspend fun films(): List<FilmData>
}