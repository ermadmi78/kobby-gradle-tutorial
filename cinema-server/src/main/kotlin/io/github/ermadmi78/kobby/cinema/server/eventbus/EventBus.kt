package io.github.ermadmi78.kobby.cinema.server.eventbus

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.FilmDto
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.springframework.stereotype.Component

/**
 * Created on 16.10.2021
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Component
class EventBus {
    private val filmCreatedBus: MutableSharedFlow<FilmDto> = MutableSharedFlow(
        extraBufferCapacity = 10,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    suspend fun fireFilmCreated(film: FilmDto) =
        filmCreatedBus.emit(film)

    fun filmCreatedFlow(): Flow<FilmDto> =
        filmCreatedBus.asSharedFlow()
}