package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.coercing

import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.BooleanValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.math.BigDecimal
import java.util.*

/**
 * Created on 15.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class BooleanCoercing : Coercing<Boolean, Boolean> {
    private fun errorMessage(input: Any): String =
        "Expected type 'Boolean' but was '${input::class.java.simpleName}'"

    @Throws(CoercingSerializeException::class)
    private fun convert(input: Any): Boolean = when (input) {
        is Boolean -> input
        is String -> input.toBoolean()
        is Number -> try {
            BigDecimal(input.toString()).compareTo(BigDecimal.ZERO) != 0
        } catch (e: Exception) {
            throw CoercingSerializeException(errorMessage(input), e)
        }

        else -> throw CoercingSerializeException(errorMessage((input)))
    }

    @Throws(CoercingSerializeException::class)
    override fun serialize(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Boolean = convert(input)

    @Throws(CoercingParseValueException::class)
    override fun parseValue(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Boolean = convert(input)

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Boolean = when (input) {
        is BooleanValue -> convert(input.isValue)
        else -> throw CoercingSerializeException(errorMessage((input)))
    }
}