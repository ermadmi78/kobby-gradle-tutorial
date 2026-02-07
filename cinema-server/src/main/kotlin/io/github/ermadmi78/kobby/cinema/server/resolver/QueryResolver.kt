package io.github.ermadmi78.kobby.cinema.server.resolver

import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.FilmData
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.QueryResolutionModel
import io.github.ermadmi78.kobby.cinema.server.dao.CinemaDao
import org.springframework.stereotype.Component

/**
 * Created on 23.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Component
class QueryResolver(
    private val cinemaDao: CinemaDao
) : QueryResolutionModel {
    override suspend fun film(id: Long): FilmData? = cinemaDao.findFilm(id)?.let {
        FilmData(it.id, it.title)
    }

    override suspend fun films(): List<FilmData> = cinemaDao.findFilms().map {
        FilmData(it.id, it.title)
    }
}