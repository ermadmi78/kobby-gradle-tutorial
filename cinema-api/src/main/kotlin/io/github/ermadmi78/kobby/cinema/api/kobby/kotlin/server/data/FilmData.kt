package io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.data

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.CinemaData

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
data class FilmData(
    val id: Long? = null,
    val title: String? = null,
    val __extensions: Map<String, Any?> = emptyMap()
) : CinemaData {
    override operator fun get(property: String): Any? = when (property) {
        "id" -> id
        "title" -> title
        else -> __extensions[property]
    }
}