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
class ResolutionContext(
    val environment: DataFetchingEnvironment
) : AbstractCoroutineContextElement(ResolutionContext) {
    companion object Key : CoroutineContext.Key<ResolutionContext>

    private val mutex = Mutex()

    private var errors: MutableList<GraphQLError>? = null
    private var extensions: MutableMap<Any, Any?>? = null

    suspend fun addError(error: GraphQLError): Unit = mutex.withLock {
        if (this.errors == null) {
            this.errors = mutableListOf()
        }
        this.errors!!.add(error)
    }

    suspend fun addErrors(errors: Iterable<GraphQLError>): Unit = mutex.withLock {
        if (this.errors == null) {
            this.errors = mutableListOf()
        }
        this.errors!!.addAll(errors)
    }

    suspend fun extractErrors(): List<GraphQLError>? = mutex.withLock {
        this.errors?.let { ArrayList(it) }
    }

    suspend fun putExtension(key: String, value: Any?): Any? = mutex.withLock {
        if (this.extensions == null) {
            this.extensions = mutableMapOf()
        }
        this.extensions!!.put(key, value)
    }

    suspend fun putExtensions(extensions: Map<String, Any?>): Unit = mutex.withLock {
        if (this.extensions == null) {
            this.extensions = mutableMapOf()
        }
        this.extensions!!.putAll(extensions)
    }

    suspend fun extractExtensions(): Map<Any, Any?>? = mutex.withLock {
        this.extensions?.let { LinkedHashMap(it) }
    }
}