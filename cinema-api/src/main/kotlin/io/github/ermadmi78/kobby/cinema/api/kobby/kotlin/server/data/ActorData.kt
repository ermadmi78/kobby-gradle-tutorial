package io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.data

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server.CinemaData

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
data class ActorData(
    val id: Long? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val __extensions: Map<String, Any?> = emptyMap()
) : CinemaData {
    override operator fun get(property: String): Any? = when (property) {
        "id" -> id
        "firstName" -> firstName
        "lastName" -> lastName
        else -> __extensions[property]
    }
}
