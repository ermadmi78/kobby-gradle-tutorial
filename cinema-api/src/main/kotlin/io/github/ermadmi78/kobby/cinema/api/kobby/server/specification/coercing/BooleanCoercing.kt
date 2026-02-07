package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.coercing

import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.BooleanValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

/**
 * Created on 15.03.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class BooleanCoercing : Coercing<Boolean, Boolean> {
    private fun convert(input: Any): Boolean = when (input) {
        is Boolean -> input
        is String -> input.lowercase().toBooleanStrict()
        is Int -> input != 0
        is Long -> input != 0L
        is BigInteger -> input.compareTo(BigInteger.ZERO) != 0
        is BigDecimal -> input.compareTo(BigDecimal.ZERO) != 0
        is Number -> BigDecimal(input.toString()).compareTo(BigDecimal.ZERO) != 0
        else -> throw IllegalArgumentException("Unexpected input type: '${input::class.java.simpleName}'")
    }

    @Throws(CoercingSerializeException::class)
    override fun serialize(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Boolean = try {
        convert(input)
    } catch (e: Exception) {
        throw CoercingSerializeException("Expected type 'Boolean' but was '${input::class.java.simpleName}'", e)
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Boolean = try {
        convert(input)
    } catch (e: Exception) {
        throw CoercingParseValueException("Expected type 'Boolean' but was '${input::class.java.simpleName}'", e)
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Boolean {
        if (input !is BooleanValue) {
            throw CoercingParseLiteralException(
                "Expected AST type 'BooleanValue' but was '${input::class.java.simpleName}'"
            )
        }

        return input.isValue
    }
}