package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification

import graphql.schema.GraphQLScalarType
import io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.coercing.*

/**
 * Created on 15.03.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */

val DEDAULT_ID_SCALAR = GraphQLScalarType.newScalar()
    .name("ID")
    .description("Cinema ID scalar")
    .coercing(LongCoercing())
    .build()

val DEDAULT_INT_SCALAR = GraphQLScalarType.newScalar()
    .name("Int")
    .description("Cinema Int scalar")
    .coercing(IntCoercing())
    .build()

val DEDAULT_LONG_SCALAR = GraphQLScalarType.newScalar()
    .name("Long")
    .description("Cinema Long scalar")
    .coercing(LongCoercing())
    .build()

val DEDAULT_FLOAT_SCALAR = GraphQLScalarType.newScalar()
    .name("Float")
    .description("Cinema Float scalar")
    .coercing(DoubleCoercing())
    .build()

val DEDAULT_DOUBLE_SCALAR = GraphQLScalarType.newScalar()
    .name("Double")
    .description("Cinema Double scalar")
    .coercing(DoubleCoercing())
    .build()

val DEDAULT_STRING_SCALAR = GraphQLScalarType.newScalar()
    .name("String")
    .description("Cinema String scalar")
    .coercing(StringCoercing())
    .build()

val DEDAULT_BOOLEAN_SCALAR = GraphQLScalarType.newScalar()
    .name("Boolean")
    .description("Cinema Boolean scalar")
    .coercing(BooleanCoercing())
    .build()