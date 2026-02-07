package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.program.type

import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLList.list
import graphql.schema.GraphQLNonNull.nonNull
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLTypeReference.typeRef

/**
 * Created on 22.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object FilmType {
    fun build(): GraphQLObjectType = GraphQLObjectType.newObject()
        .name("Film")
        .field(
            GraphQLFieldDefinition.newFieldDefinition()
                .name("id")
                .type(nonNull(typeRef("ID")))
        )
        .field(
            GraphQLFieldDefinition.newFieldDefinition()
                .name("title")
                .type(nonNull(typeRef("String")))
        )
        .field(
            GraphQLFieldDefinition.newFieldDefinition()
                .name("actors")
                .type(nonNull(list(nonNull(typeRef("Actor")))))
        )
        .build()
}