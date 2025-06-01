package io.github.ermadmi78.kobby.cinema.client

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.*
import io.github.ermadmi78.kobby.cinema.api.kobby.kotlin.adapter.ktor.CinemaCompositeKtorAdapter
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.reflect.KClass

/**
 * Created on 16.10.2021
 *
 * @author Dmitry Ermakov (ermadmi78@gmail.com)
 */

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@SpringBootApplication
class Application : CommandLineRunner {
    override fun run(vararg args: String?): Unit = runBlocking {
        // Instantiate DSL context
        val context = cinemaContextOf(createKtorAdapter())

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

        launch(Dispatchers.Default) {
            context.subscription {
                filmCreated {
                    id
                    title
                }
            }.subscribe {
                subscribed.complete()

                // We listen to the first 3 films and exit
                for (i in 0 until 3) {
                    val film = receive().filmCreated
                    println("<< Film created: ${film.title}")
                }
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
                id
                title
                actors {
                    id
                    firstName
                    lastName
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
                id
                title
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
                id
                title
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
                id
                title
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
                id
                title
                actors {
                    id
                    firstName
                    lastName
                }
            }
        }

        allFilms.films.forEach { film ->
            println(film.title)
            film.actors.forEach { actor ->
                println("  ${actor.firstName} ${actor.lastName}")
            }
        }

        println()
        println("******************************************************************")
        println("**        When we just need to build a query string             **")
        println("******************************************************************")
        println()

        val (myQuery, myQueryArgs) = buildCinemaQuery {
            film(3) {
                id
                title
                actors {
                    id
                    firstName
                    lastName
                }
            }
        }
        println(myQuery)
        println(myQueryArgs)
        println()

        val (myMutation, myMutationArgs) = buildCinemaMutation {
            createFilm("My film") {
                id
                title
            }
        }
        println(myMutation)
        println(myMutationArgs)
        println()

        val (mySubscription, miSubscriptionArgs) = buildCinemaSubscription {
            filmCreated {
                id
                title
            }
        }
        println(mySubscription)
        println(miSubscriptionArgs)

        println()
        println("##################################################################")
        println("##                       Finish                                 ##")
        println("##################################################################")
        println()
    }

    private fun createKtorAdapter(): CinemaAdapter {
        // Create Ktor http client
        val client = HttpClient(CIO) {
            install(WebSockets)
        }

        // Create Jackson object mapper
        val mapper = jacksonObjectMapper()
            .registerModule(ParameterNamesModule(JsonCreator.Mode.PROPERTIES))

        // Create default implementation of CinemaAdapter
        // Note, you can write your own implementation
        return CinemaCompositeKtorAdapter(
            client,
            "http://localhost:8080/graphql",
            "ws://localhost:8080/graphql",
            object : CinemaMapper {
                override fun serialize(value: Any): String =
                    mapper.writeValueAsString(value)

                override fun <T : Any> deserialize(content: String, contentType: KClass<T>): T =
                    mapper.readValue(content, contentType.java)
            }
        ) {
            println(">> ${it.query}")
            if (!it.variables.isNullOrEmpty()) {
                println(">> ${it.variables}")
            }
        }
    }
}