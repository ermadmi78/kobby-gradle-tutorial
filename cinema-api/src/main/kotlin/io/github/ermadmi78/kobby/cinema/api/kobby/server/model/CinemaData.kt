package io.github.ermadmi78.kobby.cinema.api.kobby.server.model

/**
 * Created on 16.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
interface CinemaData {
    operator fun get(property: String): Any?
}