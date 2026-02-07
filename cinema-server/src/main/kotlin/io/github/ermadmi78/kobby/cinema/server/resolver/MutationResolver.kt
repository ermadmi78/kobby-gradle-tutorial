package io.github.ermadmi78.kobby.cinema.server.resolver

import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.FilmData
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.MutationResolutionModel
import io.github.ermadmi78.kobby.cinema.server.dao.CinemaDao
import io.github.ermadmi78.kobby.cinema.server.eventbus.EventBus
import org.springframework.stereotype.Component

/**
 * Created on 23.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Component
class MutationResolver(
    private val cinemaDao: CinemaDao,
    private val eventBus: EventBus
) : MutationResolutionModel {
    override suspend fun createFilm(title: String): FilmData = cinemaDao.createFilm(title).let {
        FilmData(it.id, it.title)
    }.also {
        eventBus.fireFilmCreated(it)
    }
}