package io.github.ermadmi78.kobby.cinema.server.resolver

import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.ActorData
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.FilmData
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.FilmResolutionModel
import io.github.ermadmi78.kobby.cinema.server.dao.CinemaDao
import org.springframework.stereotype.Component

/**
 * Created on 23.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Component
class FilmResolver(
    private val cinemaDao: CinemaDao
) : FilmResolutionModel {
    override suspend fun actors(source: FilmData): List<ActorData> =
        cinemaDao.findFilm(source.id!!)?.actors?.map {
            ActorData(it.id, it.firstName, it.lastName)
        } ?: emptyList()
}