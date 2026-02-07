package io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime

import graphql.GraphQLError
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * Created on 16.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class ResolutionEnvironment(
    val dataFetchingEnvironment: DataFetchingEnvironment
) : AbstractCoroutineContextElement(ResolutionEnvironment) {
    companion object Key : CoroutineContext.Key<ResolutionEnvironment>

    private val mutex = Mutex()

    private var context: MutableMap<String, Any?>? = null

    private var graphQLErrors: MutableList<GraphQLError>? = null
    private var graphQLExtensions: MutableMap<Any, Any?>? = null

    suspend fun getFromContext(key: String): Any? = mutex.withLock {
        context?.get(key)
    }

    suspend fun putToContext(key: String, value: Any?): Any? = mutex.withLock {
        if (context == null) {
            context = mutableMapOf()
        }
        context!!.put(key, value)
    }

    suspend fun addGraphQLError(error: GraphQLError): Unit = mutex.withLock {
        if (graphQLErrors == null) {
            graphQLErrors = mutableListOf()
        }
        graphQLErrors!!.add(error)
    }

    suspend fun addGraphQLErrors(errors: Iterable<GraphQLError>): Unit = mutex.withLock {
        if (graphQLErrors == null) {
            graphQLErrors = mutableListOf()
        }
        graphQLErrors!!.addAll(errors)
    }

    suspend fun extractGraphQLErrors(): List<GraphQLError>? = mutex.withLock {
        graphQLErrors?.let { ArrayList(it) }
    }

    suspend fun putGraphQLExtension(key: String, value: Any?): Any? = mutex.withLock {
        if (graphQLExtensions == null) {
            graphQLExtensions = mutableMapOf()
        }
        graphQLExtensions!!.put(key, value)
    }

    suspend fun putGraphQLExtensions(extensions: Map<String, Any?>): Unit = mutex.withLock {
        if (graphQLExtensions == null) {
            graphQLExtensions = mutableMapOf()
        }
        graphQLExtensions!!.putAll(extensions)
    }

    suspend fun extractGraphQLExtensions(): Map<Any, Any?>? = mutex.withLock {
        graphQLExtensions?.let { LinkedHashMap(it) }
    }
}