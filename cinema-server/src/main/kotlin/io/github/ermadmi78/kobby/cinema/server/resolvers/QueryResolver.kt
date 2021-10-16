package io.github.ermadmi78.kobby.cinema.server.resolvers

import graphql.kickstart.tools.GraphQLQueryResolver
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.FilmDto
import io.github.ermadmi78.kobby.cinema.server.dao.CinemaDao
import org.springframework.stereotype.Component

/**
 * Created on 16.10.2021
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Component
class QueryResolver(
    private val cinemaDao: CinemaDao
) : GraphQLQueryResolver {
    suspend fun film(id: Long): FilmDto? = cinemaDao.findFilm(id)
    suspend fun films(): List<FilmDto> = cinemaDao.findFilms()
}