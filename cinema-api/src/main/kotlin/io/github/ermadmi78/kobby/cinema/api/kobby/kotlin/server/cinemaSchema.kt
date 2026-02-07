@file:Suppress("UNCHECKED_CAST")

package io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.server

import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.LightDataFetcher
import java.util.function.Supplier

/**
 * Created on 07.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */

interface CinemaData {
    operator fun get(property: String): Any?
}

internal class CinemaDataFetcher(
    private val property: String
) : LightDataFetcher<Any?> {
    override fun get(
        fieldDefinition: GraphQLFieldDefinition,
        sourceObject: Any?,
        environmentSupplier: Supplier<DataFetchingEnvironment>
    ): Any? = (sourceObject as? CinemaData)?.let { it[property] }

    override fun get(
        environment: DataFetchingEnvironment
    ): Any? = (environment.getSource<CinemaData>())?.let { it[property] }
}