package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.coercing

import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.IntValue
import graphql.language.StringValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

/**
 * Created on 15.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class IntCoercing : Coercing<Int, Int> {
    private fun errorMessage(input: Any): String =
        "Expected type 'Int' but was '${input::class.java.simpleName}'"

    @Throws(CoercingSerializeException::class)
    private fun convert(input: Any): Int = when (input) {
        is Byte -> input.toInt()
        is Short -> input.toInt()
        is Int -> input

        is BigInteger -> try {
            input.intValueExact()
        } catch (e: Exception) {
            throw CoercingSerializeException(errorMessage(input), e)
        }

        is BigDecimal -> try {
            input.intValueExact()
        } catch (e: Exception) {
            throw CoercingSerializeException(errorMessage(input), e)
        }

        is Number -> try {
            BigDecimal(input.toString()).intValueExact()
        } catch (e: Exception) {
            throw CoercingSerializeException(errorMessage(input), e)
        }

        is String -> try {
            input.toInt()
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
    ): Int = convert(input)

    @Throws(CoercingParseValueException::class)
    override fun parseValue(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Int = convert(input)

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Int = when (input) {
        is IntValue -> convert(input.value)
        is StringValue -> convert(input.value)
        else -> throw CoercingSerializeException(errorMessage((input)))
    }
}