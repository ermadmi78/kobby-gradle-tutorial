package io.github.ermadmi78.kobby.cinema.server.controller

import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.FilmDto
import io.github.ermadmi78.kobby.cinema.server.eventbus.EventBus
import kotlinx.coroutines.reactor.asFlux
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux

/**
 * Created on 16.10.2021
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Controller
@SchemaMapping(typeName = "Subscription")
class SubscriptionController(
    private val eventBus: EventBus
) {
    @SchemaMapping
    suspend fun filmCreated(): Flux<FilmDto> = eventBus.filmCreatedFlow().asFlux()
}