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
class LongCoercing : Coercing<Long, Long> {
    private fun errorMessage(input: Any): String =
        "Expected type 'Long' but was '${input::class.java.simpleName}'"

    @Throws(CoercingSerializeException::class)
    private fun convert(input: Any): Long = when (input) {
        is Byte -> input.toLong()
        is Short -> input.toLong()
        is Int -> input.toLong()
        is Long -> input

        is BigInteger -> try {
            input.longValueExact()
        } catch (e: Exception) {
            throw CoercingSerializeException(errorMessage(input), e)
        }

        is BigDecimal -> try {
            input.longValueExact()
        } catch (e: Exception) {
            throw CoercingSerializeException(errorMessage(input), e)
        }

        is Number -> try {
            BigDecimal(input.toString()).longValueExact()
        } catch (e: Exception) {
            throw CoercingSerializeException(errorMessage(input), e)
        }

        is String -> try {
            input.toLong()
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
    ): Long = convert(input)

    @Throws(CoercingParseValueException::class)
    override fun parseValue(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Long = convert(input)

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(
        input: Value<*>,
        variables: CoercedVariables,
        graphQLContext: GraphQLContext,
        locale: Locale
    ): Long = when (input) {
        is IntValue -> convert(input.value)
        is StringValue -> convert(input.value)
        else -> throw CoercingSerializeException(errorMessage((input)))
    }
}