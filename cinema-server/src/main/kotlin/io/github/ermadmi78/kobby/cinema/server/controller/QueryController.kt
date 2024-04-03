package io.github.ermadmi78.kobby.cinema.server.controller

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.FilmDto
import io.github.ermadmi78.kobby.cinema.server.dao.CinemaDao
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

/**
 * Created on 16.10.2021
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Controller
@SchemaMapping(typeName = "Query")
class QueryController(
    private val cinemaDao: CinemaDao
) {
    @SchemaMapping
    suspend fun film(@Argument id: Long): FilmDto? = cinemaDao.findFilm(id)

    @SchemaMapping
    suspend fun films(): List<FilmDto> = cinemaDao.findFilms()
}