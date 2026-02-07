package io.github.ermadmi78.kobby.cinema.server.resolver

import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLNamedSchemaElement
import io.github.ermadmi78.kobby.cinema.api.kobby.server.runtime.ResolutionAspect
import kotlinx.coroutines.future.asDeferred
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

/**
 * Created on 23.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Component
class CinemaResolutionAspect : ResolutionAspect {
    override suspend fun <T> around(
        environment: DataFetchingEnvironment,
        resolution: suspend () -> T
    ): T {
        if (environment.getSource<Any?>() != null) {
            return resolution() // Not a root resolver — just execute it.
        }

        val securityContextProvider: Mono<SecurityContext>? = environment.graphQlContext
            .get<Mono<SecurityContext>>(SecurityContext::class.java)

        val authentication: Authentication = securityContextProvider?.toFuture()?.asDeferred()?.await()
            ?.authentication ?: throw AccessDeniedException("Not authorized")

        val start = System.currentTimeMillis()
        val result = resolution()

        println(
            "[${authentication.name}]: " +
                    "${(environment.parentType as? GraphQLNamedSchemaElement)?.name}.${environment.field?.name} " +
                    "${System.currentTimeMillis() - start} millis"
        )

        return result
    }
}