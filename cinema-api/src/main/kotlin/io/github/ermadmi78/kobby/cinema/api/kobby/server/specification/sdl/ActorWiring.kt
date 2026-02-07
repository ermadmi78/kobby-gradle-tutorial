package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.sdl

import graphql.schema.PropertyDataFetcher.fetching
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.data.ActorData

/**
 * Created on 22.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object ActorWiring {
    fun register(
        builder: RuntimeWiring.Builder
    ) {
        builder.type(
            newTypeWiring("Actor")
                .dataFetcher(
                    "id",
                    fetching<Long, ActorData> { it.id }
                )
                .dataFetcher(
                    "firstName",
                    fetching<String, ActorData> { it.firstName }
                )
                .dataFetcher(
                    "lastName",
                    fetching<String, ActorData> { it.lastName }
                )
        )
    }
}