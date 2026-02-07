package io.github.ermadmi78.kobby.cinema.server.serverless

import graphql.scalars.ExtendedScalars
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.CinemaContext
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.cinemaContextOf
import io.github.ermadmi78.kobby.cinema.api.kobby.server.buildCinemaSchemaUsingProgram
import io.github.ermadmi78.kobby.cinema.api.kobby.server.buildCinemaSchemaUsingSDL
import io.github.ermadmi78.kobby.cinema.server.dao.CinemaDao
import io.github.ermadmi78.kobby.cinema.server.eventbus.EventBus
import io.github.ermadmi78.kobby.cinema.server.resolver.FilmResolver
import io.github.ermadmi78.kobby.cinema.server.resolver.MutationResolver
import io.github.ermadmi78.kobby.cinema.server.resolver.QueryResolver
import io.github.ermadmi78.kobby.cinema.server.resolver.SubscriptionResolver
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.io.FileReader
import java.io.Reader

/**
 * Created on 09.03.2026
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */
class ServerlessTest : AnnotationSpec() {
    @Test
    suspend fun testSchemaProgramBuilder() {
        val cinemaDao = CinemaDao()
        val eventBus = EventBus()
        val queryResolver = QueryResolver(cinemaDao)
        val mutationResolver = MutationResolver(cinemaDao, eventBus)
        val subscriptionResolver = SubscriptionResolver(eventBus)
        val filmResolver = FilmResolver(cinemaDao)

        val schema = buildCinemaSchemaUsingProgram(
            queryResolver,
            mutationResolver,
            subscriptionResolver,
            filmResolver,
            ExtendedScalars.GraphQLLong
        )

        val context = cinemaContextOf(CinemaServerlessAdapter(schema))
        execute(context)
    }

    @Test
    @Ignore
    suspend fun testSchemaSDLBuilder() {
        val schemas: Sequence<Reader> = PathMatchingResourcePatternResolver().getResources(
            "classpath*:io/github/ermadmi78/kobby/cinema/api/**/*.graphqls"
        ).asSequence()
            .filter { it.exists() && it.isFile }
            .map { FileReader(it.file) }

        val cinemaDao = CinemaDao()
        val eventBus = EventBus()
        val queryResolver = QueryResolver(cinemaDao)
        val mutationResolver = MutationResolver(cinemaDao, eventBus)
        val subscriptionResolver = SubscriptionResolver(eventBus)
        val filmResolver = FilmResolver(cinemaDao)

        val schema = buildCinemaSchemaUsingSDL(
            schemas,
            queryResolver,
            mutationResolver,
            subscriptionResolver,
            filmResolver,
            ExtendedScalars.GraphQLLong
        )

        val context = cinemaContextOf(CinemaServerlessAdapter(schema))
        execute(context)
    }

    private suspend fun execute(context: CinemaContext) {
        println()
        println()
        println("##################################################################")
        println("##                       Start                                  ##")
        println("##################################################################")
        println()
        println("******************************************************************")
        println("**          Subscribe to new films asynchronously               **")
        println("******************************************************************")
        println()
        val subscribed = Job()
        val closed = Job()

        CoroutineScope(Dispatchers.Default).launch {
            context.subscription {
                filmCreated {
                    id()
                    title()
                }
            }.subscribe {
                subscribed.complete()

                // We listen to the first 3 films and exit
                for (i in 0 until 3) {
                    val film = receive().filmCreated
                    println("<< Film created: ${film.title}")
                }

                closed.complete()
            }
        }

        // Wait for the subscription to be established to avoid races
        subscribed.join()

        println()
        println("******************************************************************")
        println("**                   Select film by id                          **")
        println("******************************************************************")
        println()

        val result = context.query {
            film(id = 0L) {
                id()
                title()
                actors {
                    id()
                    firstName()
                    lastName()
                }
            }
        }

        result.film?.also { film ->
            println(film.title)
            film.actors.forEach { actor ->
                println("  ${actor.firstName} ${actor.lastName}")
            }
        }

        println()
        println("******************************************************************")
        println("**                   Create first film                          **")
        println("******************************************************************")
        println()

        val first = context.mutation {
            createFilm("First") {
                id()
                title()
            }
        }

        first.createFilm.also { film ->
            println(film.title)
        }

        println()
        println("******************************************************************")
        println("**                   Create second film                         **")
        println("******************************************************************")
        println()

        val second = context.mutation {
            createFilm("Second") {
                id()
                title()
            }
        }

        second.createFilm.also { film ->
            println(film.title)
        }

        println()
        println("******************************************************************")
        println("**                   Create third film                          **")
        println("******************************************************************")
        println()

        val third = context.mutation {
            createFilm("Third") {
                id()
                title()
            }
        }

        third.createFilm.also { film ->
            println(film.title)
        }

        println()
        println("******************************************************************")
        println("**                   Select all films                           **")
        println("******************************************************************")
        println()

        val allFilms = context.query {
            films {
                id()
                title()
                actors {
                    id()
                    firstName()
                    lastName()
                }
            }
        }

        allFilms.films.forEach { film ->
            println(film.title)
            film.actors.forEach { actor ->
                println("  ${actor.firstName} ${actor.lastName}")
            }
        }

        closed.join()
    }
}