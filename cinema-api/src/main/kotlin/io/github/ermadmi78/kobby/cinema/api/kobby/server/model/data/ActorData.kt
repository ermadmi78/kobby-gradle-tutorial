package io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data

import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.CinemaData

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
data class ActorData(
    val id: Long? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val __localContext: Map<String, Any?> = emptyMap()
) : CinemaData {
    override operator fun get(property: String): Any? =
        __localContext[property]
}
