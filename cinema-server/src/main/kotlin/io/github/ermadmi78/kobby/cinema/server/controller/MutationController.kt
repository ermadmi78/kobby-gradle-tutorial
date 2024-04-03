package io.github.ermadmi78.kobby.cinema.server.controller

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.FilmDto
import io.github.ermadmi78.kobby.cinema.server.dao.CinemaDao
import io.github.ermadmi78.kobby.cinema.server.eventbus.EventBus
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

/**
 * Created on 16.10.2021
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Controller
@SchemaMapping(typeName = "Mutation")
class MutationController(
    private val cinemaDao: CinemaDao,
    private val eventBus: EventBus
) {
    @SchemaMapping
    suspend fun createFilm(@Argument title: String): FilmDto = cinemaDao.createFilm(title).also {
        eventBus.fireFilmCreated(it)
    }
}