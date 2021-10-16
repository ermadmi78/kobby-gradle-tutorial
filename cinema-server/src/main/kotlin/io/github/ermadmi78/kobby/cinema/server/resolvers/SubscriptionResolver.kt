package io.github.ermadmi78.kobby.cinema.server.resolvers

import graphql.kickstart.tools.GraphQLSubscriptionResolver
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.FilmDto
import io.github.ermadmi78.kobby.cinema.server.eventbus.EventBus
import kotlinx.coroutines.reactive.asPublisher
import org.reactivestreams.Publisher
import org.springframework.stereotype.Component

/**
 * Created on 16.10.2021
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Component
class SubscriptionResolver(
    private val eventBus: EventBus
) : GraphQLSubscriptionResolver {
    suspend fun filmCreated(): Publisher<FilmDto> = eventBus.filmCreatedFlow().asPublisher()
}