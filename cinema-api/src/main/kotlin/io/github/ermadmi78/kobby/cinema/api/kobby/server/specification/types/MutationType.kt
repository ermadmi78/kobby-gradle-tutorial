package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.types

import graphql.schema.GraphQLArgument
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLNonNull.nonNull
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLTypeReference.typeRef

/**
 * Created on 15.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object MutationType {
    fun build(
        scalarString: GraphQLScalarType
    ): GraphQLObjectType = GraphQLObjectType.newObject()
        .name("Mutation")
        .field(
            GraphQLFieldDefinition.newFieldDefinition()
                .name("createFilm")
                .type(nonNull(typeRef("Film")))
                .argument(
                    GraphQLArgument.newArgument()
                        .name("title")
                        .type(nonNull(scalarString))
                )
        )
        .build()
}