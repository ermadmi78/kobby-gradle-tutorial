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
class CinemaResolutionEnvironment(
    val dataFetchingEnvironment: DataFetchingEnvironment
) : AbstractCoroutineContextElement(CinemaResolutionEnvironment) {
    companion object Key : CoroutineContext.Key<CinemaResolutionEnvironment>

    private val mutex = Mutex()

    private var context: MutableMap<String, Any?>? = null

    private var errors: MutableList<GraphQLError>? = null
    private var extensions: MutableMap<String, Any?>? = null

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
        if (errors == null) {
            errors = mutableListOf()
        }
        errors!!.add(error)
    }
}