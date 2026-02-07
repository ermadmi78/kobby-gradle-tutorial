package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.program.type

import graphql.schema.GraphQLArgument
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLNonNull.nonNull
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLTypeReference.typeRef

/**
 * Created on 15.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object MutationType {
    fun build(): GraphQLObjectType = GraphQLObjectType.newObject()
        .name("Mutation")
        .field(
            GraphQLFieldDefinition.newFieldDefinition()
                .name("createFilm")
                .type(nonNull(typeRef("Film")))
                .argument(
                    GraphQLArgument.newArgument()
                        .name("title")
                        .type(nonNull(typeRef("String")))
                )
        )
        .build()
}