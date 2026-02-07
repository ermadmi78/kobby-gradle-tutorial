package io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.runtime

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.data.FilmData

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
interface CinemaMutationResolver {
    suspend fun createFilm(title: String): FilmData
}