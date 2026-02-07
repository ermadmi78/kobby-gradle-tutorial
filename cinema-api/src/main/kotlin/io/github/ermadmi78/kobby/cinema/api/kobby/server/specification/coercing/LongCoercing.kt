package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.coercing

import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.IntValue
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
class LongCoercing : Coercing<Long, Long> {
    private fun convert(input: Any): Long = when (input) {
        is Long -> input
        is Int -> input.toLong()
        is BigInteger -> input.longValueExact()
        is BigDecimal -> input.longValueExact()
        is Number, String -> BigDecimal(input.toString()).longValueExact()
        else -> throw IllegalArgumentException("Unexpected input type: '${input::class.java.simpleName}'")
    }

    @Throws(CoercingSerializeException::class)
    override fun serialize(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Long = try {
        convert(input)
    } catch (e: Exception) {
        throw CoercingSerializeException("Expected type 'Long' but was '${input::class.java.simpleName}'", e)
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Long = try {
        convert(input)
    } catch (e: Exception) {
        throw CoercingParseValueException("Expected type 'Long' but was '${input::class.java.simpleName}'", e)
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Long {
        if (input !is IntValue) {
            throw CoercingParseLiteralException(
                "Expected AST type 'IntValue' but was '${input::class.java.simpleName}'"
            )
        }

        val value: BigInteger = input.value
        return try {
            value.longValueExact()
        } catch (e: Exception) {
            throw CoercingParseLiteralException("Expected AST type 'IntValue' but was '$value'", e)
        }
    }
}