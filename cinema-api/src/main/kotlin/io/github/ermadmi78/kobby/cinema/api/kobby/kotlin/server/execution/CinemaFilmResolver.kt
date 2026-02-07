package io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.execution

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.entity.Actor

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
interface CinemaFilmResolver {
    suspend fun actors(): List<Actor>
}