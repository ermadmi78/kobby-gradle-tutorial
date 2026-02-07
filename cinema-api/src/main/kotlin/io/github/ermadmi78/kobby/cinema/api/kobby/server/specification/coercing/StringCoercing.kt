package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.coercing

import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.StringValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.util.*

/**
 * Created on 15.03.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class StringCoercing : Coercing<String, String> {
    @Throws(CoercingSerializeException::class)
    override fun serialize(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): String = input.toString()

    @Throws(CoercingParseValueException::class)
    override fun parseValue(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): String = input.toString()

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): String {
        if (input !is StringValue) {
            throw CoercingParseLiteralException(
                "Expected AST type 'IntValue' but was '${input::class.java.simpleName}'"
            )
        }

        return input.value
    }
}