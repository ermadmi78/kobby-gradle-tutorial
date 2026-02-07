package io.github.ermadmi78.kobby.cinema.server

import graphql.scalars.ExtendedScalars
import io.github.ermadmi78.kobby.cinema.api.kobby.server.wireCinemaSchemaRuntime
import io.github.ermadmi78.kobby.cinema.server.resolver.CinemaResolutionAspect
import io.github.ermadmi78.kobby.cinema.server.resolver.FilmResolver
import io.github.ermadmi78.kobby.cinema.server.resolver.MutationResolver
import io.github.ermadmi78.kobby.cinema.server.resolver.QueryResolver
import io.github.ermadmi78.kobby.cinema.server.resolver.SubscriptionResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.reactive.config.EnableWebFlux

/**
 * Created on 23.02.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
@Configuration
@EnableWebFlux
class ApplicationConfiguration() {
    @Bean
    fun runtimeWiringConfigurer(
        queryResolver: QueryResolver,
        mutationResolver: MutationResolver,
        subscriptionResolver: SubscriptionResolver,
        filmResolver: FilmResolver,
        cinemaResolutionAspect: CinemaResolutionAspect
    ) = RuntimeWiringConfigurer { builder ->
        wireCinemaSchemaRuntime(
            scalarLong = ExtendedScalars.GraphQLLong,
            runtimeWiring = builder,
            queryResolver = queryResolver,
            mutationResolver = mutationResolver,
            subscriptionResolver = subscriptionResolver,
            filmResolver = filmResolver,
            aspect = cinemaResolutionAspect
        )
    }

    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain = http
        .csrf { it.disable() }
        .authorizeExchange { it.anyExchange().authenticated() }
        .httpBasic {}
        .build()

    /**
     * Basic HTTP users configuration
     */
    @Bean
    fun userDetailsService(): ReactiveUserDetailsService {
        val user: UserDetails = User.builder()
            .username("user")
            .password("{noop}user")
            .roles("USER")
            .build()
        val admin: UserDetails = User.builder()
            .username("admin")
            .password("{noop}admin")
            .roles("ADMIN")
            .build()
        return MapReactiveUserDetailsService(user, admin)
    }
}
