package io.github.ermadmi78.kobby.cinema.server.dao

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
        0L to ActorRecord(0L, "Audrey", "Tautou"),
        1L to ActorRecord(1L, "Mathieu", "Kassovitz"),
        2L to ActorRecord(2L, "Jamel", "Debbouze"),
        3L to ActorRecord(3L, "Dominique", "Pinon"),
        4L to ActorRecord(4L, "Gaspard", "Ulliel"),
        5L to ActorRecord(5L, "Guillaume", "Canet"),
        6L to ActorRecord(6L, "Gad", "Elmaleh")
    )

    private val films = mutableMapOf(
        0L to FilmRecord(0L, "Amelie"),
        1L to FilmRecord(1L, "A Very Long Engagement"),
        2L to FilmRecord(2L, "Hunting and Gathering"),
        3L to FilmRecord(3L, "Priceless")
    )

    private val filmActors = mutableMapOf(
        0L to listOf(actors[0L]!!, actors[1L]!!, actors[2L]!!, actors[3L]!!),
        1L to listOf(actors[0L]!!, actors[3L]!!, actors[4L]!!),
        2L to listOf(actors[0L]!!, actors[5L]!!),
        3L to listOf(actors[0L]!!, actors[6L]!!)
    )

    suspend fun findFilm(id: Long): FilmRecord? = mutex.withLock {
        films[id]
    }

    suspend fun findFilms(): List<FilmRecord> = mutex.withLock {
        films.values.toList()
    }

    suspend fun findFilmActors(id: Long): List<ActorRecord> = mutex.withLock {
        filmActors[id] ?: emptyList()
    }

    suspend fun createFilm(title: String): FilmRecord = mutex.withLock {
        FilmRecord(films.size.toLong(), title).also {
            films[it.id!!] = it
        }
    }
}