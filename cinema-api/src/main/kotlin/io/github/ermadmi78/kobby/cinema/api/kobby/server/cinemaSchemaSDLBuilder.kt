package io.github.ermadmi78.kobby.cinema.api.kobby.server

import graphql.Scalars
import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLSchema
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.FilmResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.MutationResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.QueryResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.model.resolver.SubscriptionResolutionModel
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.DEFAULT_CONTEXT_PROVIDER
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.DEFAULT_RESOLUTION_ASPECT
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.DEDAULT_ID_SCALAR
import io.github.ermadmi78.kobby.cinema.api.kobby.server.specification.sdl.*
import java.io.Reader
import kotlin.coroutines.CoroutineContext

/**
 * Created on 15.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */

fun buildCinemaSchemaUsingSDL(
    schemas: Sequence<Reader>,
    queryResolver: QueryResolutionModel,
    mutationResolver: MutationResolutionModel,
    subscriptionResolver: SubscriptionResolutionModel,
    filmResolver: FilmResolutionModel,
    scalarID: GraphQLScalarType = DEDAULT_ID_SCALAR,
    scalarString: GraphQLScalarType = Scalars.GraphQLString,
    aspect: ResolutionAspect = DEFAULT_RESOLUTION_ASPECT,
    coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext = DEFAULT_CONTEXT_PROVIDER
): GraphQLSchema {
    val schemaParser = SchemaParser()
    val registry = TypeDefinitionRegistry()
    for (schema in schemas) {
        registry.merge(schemaParser.parse(schema))
    }

    val wiring = RuntimeWiring.newRuntimeWiring().also {
        wireCinemaSchemaRuntime(
            it,
            queryResolver,
            mutationResolver,
            subscriptionResolver,
            filmResolver,
            scalarID,
            scalarString,
            aspect,
            coroutineContextProvider
        )
    }.build()

    return SchemaGenerator().makeExecutableSchema(registry, wiring)
}

fun wireCinemaSchemaRuntime(
    runtimeWiring: RuntimeWiring.Builder,
    queryResolver: QueryResolutionModel,
    mutationResolver: MutationResolutionModel,
    subscriptionResolver: SubscriptionResolutionModel,
    filmResolver: FilmResolutionModel,
    scalarID: GraphQLScalarType = DEDAULT_ID_SCALAR,
    scalarString: GraphQLScalarType = Scalars.GraphQLString,
    aspect: ResolutionAspect = DEFAULT_RESOLUTION_ASPECT,
    coroutineContextProvider: (DataFetchingEnvironment) -> CoroutineContext = DEFAULT_CONTEXT_PROVIDER
) {
    require(scalarID.name == "ID") {
        "For scalarID expected scalar with name 'ID', but found '${scalarID.name}'"
    }
    require(scalarString.name == "String") {
        "For scalarString expected scalar with name 'String', but found '${scalarString.name}'"
    }

    runtimeWiring
        .strictMode(false)
        .scalar(scalarID)
        .scalar(scalarString)

    QueryWiring.register(runtimeWiring, queryResolver, aspect, coroutineContextProvider)
    MutationWiring.register(runtimeWiring, mutationResolver, aspect, coroutineContextProvider)
    SubscriptionWiring.register(runtimeWiring, subscriptionResolver, aspect, coroutineContextProvider)
    FilmWiring.register(runtimeWiring, filmResolver, aspect, coroutineContextProvider)
    ActorWiring.register(runtimeWiring)
}