package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification

import graphql.schema.GraphQLScalarType
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.coercing.LongCoercing
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.coercing.StringCoercing

/**
 * Created on 15.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
object CinemaScalar {
    val GraphQLID: GraphQLScalarType = GraphQLScalarType.newScalar()
        .name("ID")
        .coercing(LongCoercing())
        .build()

    val GraphQLString: GraphQLScalarType = GraphQLScalarType.newScalar()
        .name("String")
        .coercing(StringCoercing())
        .build()
}