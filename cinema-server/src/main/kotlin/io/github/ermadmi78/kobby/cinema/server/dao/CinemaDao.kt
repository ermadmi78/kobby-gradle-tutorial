package io.github.ermadmi78.kobby.cinema.server.dao

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.ActorDto
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.FilmDto
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.stereotype.Component

/**
 * Created on 16.10.2021
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Component
class CinemaDao {
    private val mutex = Mutex()

    private val actors = mapOf(
        0L to ActorDto(0L, "Audrey", "Tautou"),
        1L to ActorDto(1L, "Mathieu", "Kassovitz"),
        2L to ActorDto(2L, "Jamel", "Debbouze"),
        3L to ActorDto(3L, "Dominique", "Pinon"),
        4L to ActorDto(4L, "Gaspard", "Ulliel"),
        5L to ActorDto(5L, "Guillaume", "Canet"),
        6L to ActorDto(6L, "Gad", "Elmaleh")
    )

    private val films = mutableMapOf(
        0L to FilmDto(0L, "Amelie", listOf(actors[0L]!!, actors[1L]!!, actors[2L]!!, actors[3L]!!)),
        1L to FilmDto(1L, "A Very Long Engagement", listOf(actors[0L]!!, actors[3L]!!, actors[4L]!!)),
        2L to FilmDto(2L, "Hunting and Gathering", listOf(actors[0L]!!, actors[5L]!!)),
        3L to FilmDto(3L, "Priceless", listOf(actors[0L]!!, actors[6L]!!))
    )

    suspend fun findFilm(id: Long): FilmDto? = mutex.withLock {
        films[id]
    }

    suspend fun findFilms(): List<FilmDto> = mutex.withLock {
        films.values.toList()
    }

    suspend fun createFilm(title: String): FilmDto = mutex.withLock {
        FilmDto(films.size.toLong(), title, listOf()).also {
            films[it.id!!] = it
        }
    }
}