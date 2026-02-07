package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.types

import graphql.schema.GraphQLArgument
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLList.list
import graphql.schema.GraphQLNonNull.nonNull
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLTypeReference.typeRef

/**
 * Created on 15.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
internal object QueryType {
    fun build(
        scalarID: GraphQLScalarType
    ): GraphQLObjectType = GraphQLObjectType.newObject()
        .name("Query")
        .field(
            GraphQLFieldDefinition.newFieldDefinition()
                .name("film")
                .type(typeRef("Film"))
                .argument(
                    GraphQLArgument.newArgument()
                        .name("id")
                        .type(nonNull(scalarID))
                )
        )
        .field(
            GraphQLFieldDefinition.newFieldDefinition()
                .name("films")
                .type(nonNull(list(nonNull(typeRef("Film")))))
        )
        .build()
}