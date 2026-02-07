package io.github.ermadmi78.kobby.cinema.server.resolver

import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.FilmData
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.SubscriptionResolutionModel
import io.github.ermadmi78.kobby.cinema.server.eventbus.EventBus
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component

/**
 * Created on 23.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Component
class SubscriptionResolver(
    private val eventBus: EventBus
) : SubscriptionResolutionModel {
    override suspend fun filmCreated(): Flow<FilmData> = eventBus.filmCreatedFlow()
}