package io.github.ermadmi78.kobby.cinema.server.resolvers

import graphql.kickstart.tools.GraphQLMutationResolver
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.FilmDto
import io.github.ermadmi78.kobby.cinema.server.dao.CinemaDao
import io.github.ermadmi78.kobby.cinema.server.eventbus.EventBus
import org.springframework.stereotype.Component

/**
 * Created on 16.10.2021
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Component
class MutationResolver(
    private val cinemaDao: CinemaDao,
    private val eventBus: EventBus
) : GraphQLMutationResolver {
    suspend fun createFilm(title: String): FilmDto = cinemaDao.createFilm(title).also {
        eventBus.fireFilmCreated(it)
    }
}