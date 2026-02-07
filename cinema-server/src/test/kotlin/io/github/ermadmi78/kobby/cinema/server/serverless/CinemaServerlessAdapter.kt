package io.github.ermadmi78.kobby.cinema.server.serverless

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import graphql.ExecutionInput
import graphql.ExecutionResult
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.CinemaAdapter
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.CinemaReceiver
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.MutationDto
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.QueryDto
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.SubscriptionDto
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.dto.graphql.*
import kotlinx.coroutines.future.await
import org.reactivestreams.Publisher
import java.util.concurrent.CompletableFuture

/**
 * Created on 09.03.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class CinemaServerlessAdapter(
    schema: GraphQLSchema
) : CinemaAdapter {
    private val graphQL = GraphQL.newGraphQL(schema).build()
    val mapper = jacksonObjectMapper()
        .registerModule(ParameterNamesModule(JsonCreator.Mode.PROPERTIES))

    override suspend fun executeQuery(
        query: String,
        variables: Map<String, Any?>
    ): QueryDto {
        val future: CompletableFuture<ExecutionResult> = graphQL.executeAsync(
            ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
        )

        val json: Map<String, Any?> = future.await().toSpecification()
        val jsonStr: String = mapper.writeValueAsString(json)

        val result = mapper.readValue(jsonStr, CinemaQueryResult::class.java)

        result.errors?.takeIf { it.isNotEmpty() }?.let {
            throw CinemaQueryException(
                "GraphQL query failed",
                CinemaRequest(query, variables),
                it,
                result.extensions,
                result.data
            )
        }
        return result.data ?: throw CinemaQueryException(
            "GraphQL query completes successfully but returns no data",
            CinemaRequest(query, variables),
            result.errors,
            result.extensions,
            null
        )
    }

    override suspend fun executeMutation(
        query: String,
        variables: Map<String, Any?>
    ): MutationDto {
        val future: CompletableFuture<ExecutionResult> = graphQL.executeAsync(
            ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
        )

        val json: Map<String, Any?> = future.await().toSpecification()
        val jsonStr: String = mapper.writeValueAsString(json)

        val result = mapper.readValue(jsonStr, CinemaMutationResult::class.java)

        result.errors?.takeIf { it.isNotEmpty() }?.let {
            throw CinemaMutationException(
                "GraphQL query failed",
                CinemaRequest(query, variables),
                it,
                result.extensions,
                result.data
            )
        }
        return result.data ?: throw CinemaMutationException(
            "GraphQL query completes successfully but returns no data",
            CinemaRequest(query, variables),
            result.errors,
            result.extensions,
            null
        )
    }

    override suspend fun executeSubscription(
        query: String,
        variables: Map<String, Any?>,
        block: suspend CinemaReceiver<SubscriptionDto>.() -> Unit
    ) {
        val subscriptionExecutionResult = graphQL.execute(
            ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
        )

        val publisher: Publisher<ExecutionResult> = subscriptionExecutionResult.getData()
        val subscriber = CinemaServerlessSubscriber<ExecutionResult>()
        publisher.subscribe(subscriber)

        subscriber.awaitSubscription()
        val receiver = CinemaReceiver {
            val json: Map<String, Any?> = subscriber.awaitNext().toSpecification()
            val jsonStr: String = mapper.writeValueAsString(json)

            val result = mapper.readValue(jsonStr, CinemaSubscriptionResult::class.java)

            result.errors?.takeIf { it.isNotEmpty() }?.let {
                throw CinemaSubscriptionException(
                    "GraphQL query failed",
                    CinemaRequest(query, variables),
                    it,
                    result.extensions,
                    result.data
                )
            }
            result.data ?: throw CinemaSubscriptionException(
                "GraphQL query completes successfully but returns no data",
                CinemaRequest(query, variables),
                result.errors,
                result.extensions,
                null
            )
        }

        try {
            block(receiver)
        } finally {
            subscriber.awaitCancel()
        }
    }
}