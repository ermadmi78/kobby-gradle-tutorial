package io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.entity.Actor

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
interface FilmResolutionModel {
    suspend fun actors(): List<Actor>
}