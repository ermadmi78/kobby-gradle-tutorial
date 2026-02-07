package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.program.code

import graphql.schema.FieldCoordinates.coordinates
import graphql.schema.GraphQLCodeRegistry
import graphql.schema.PropertyDataFetcher.fetching
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.ActorData

/**
 * Created on 22.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object ActorCode {
    fun register(
        builder: GraphQLCodeRegistry.Builder
    ) {
        builder
            .dataFetcher(
                coordinates("Actor", "id"),
                fetching<Long, ActorData> { it.id }
            )
            .dataFetcher(
                coordinates("Actor", "firstName"),
                fetching<String, ActorData> { it.firstName }
            )
            .dataFetcher(
                coordinates("Actor", "lastName"),
                fetching<String, ActorData> { it.lastName }
            )
    }
}