package io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.coercing

import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.FloatValue
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
class DoubleCoercing : Coercing<Double, Double> {
    private fun convert(input: Any): Double = when (input) {
        is Double -> input
        is Float -> input.toDouble()
        is BigInteger -> input.toDouble()
        is BigDecimal -> input.toDouble()
        is Number, String -> BigDecimal(input.toString()).toDouble()
        else -> throw IllegalArgumentException("Unexpected input type: '${input::class.java.simpleName}'")
    }

    @Throws(CoercingSerializeException::class)
    override fun serialize(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Double = try {
        convert(input)
    } catch (e: Exception) {
        throw CoercingSerializeException("Expected type 'Double' but was '${input::class.java.simpleName}'", e)
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Double = try {
        convert(input)
    } catch (e: Exception) {
        throw CoercingParseValueException("Expected type 'Double' but was '${input::class.java.simpleName}'", e)
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Double = when (input) {
        is FloatValue -> input.value.toDouble()
        is IntValue -> input.value.toDouble()
        else -> throw CoercingParseLiteralException(
            "Expected AST type 'FloatValue' or 'IntValue' but was '${input::class.java.simpleName}'"
        )
    }
}