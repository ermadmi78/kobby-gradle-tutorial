@file:Suppress("UNCHECKED_CAST")

package io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */

interface CinemaData {
    operator fun get(property: String): Any?
}