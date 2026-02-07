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
class IntCoercing : Coercing<Int, Int> {
    private fun convert(input: Any): Int = when (input) {
        is Int -> input
        is BigInteger -> input.intValueExact()
        is BigDecimal -> input.intValueExact()
        is Number, String -> BigDecimal(input.toString()).intValueExact()
        else -> throw IllegalArgumentException("Unexpected input type: '${input::class.java.simpleName}'")
    }

    @Throws(CoercingSerializeException::class)
    override fun serialize(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Int = try {
        convert(input)
    } catch (e: Exception) {
        throw CoercingSerializeException("Expected type 'Int' but was '${input::class.java.simpleName}'", e)
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Int = try {
        convert(input)
    } catch (e: Exception) {
        throw CoercingParseValueException("Expected type 'Int' but was '${input::class.java.simpleName}'", e)
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Int {
        if (input !is IntValue) {
            throw CoercingParseLiteralException(
                "Expected AST type 'IntValue' but was '${input::class.java.simpleName}'"
            )
        }

        val value: BigInteger = input.value
        return try {
            value.intValueExact()
        } catch (e: Exception) {
            throw CoercingParseLiteralException("Expected AST type 'IntValue' but was '$value'", e)
        }
    }
}