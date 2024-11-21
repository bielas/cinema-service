package com.fourthwall.cinemaservice.e2e

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieApiTest {

    @LocalServerPort
    private var port: Int = 0

    private val webClient: WebClient = WebClient.create()

    private fun baseUrl(endpoint: String): String = "http://localhost:$port/v1/movies$endpoint"

    @Test
    fun `should get movie by ID`() {
        val movieId = "tt0232500"
        val response = webClient.get()
            .uri(baseUrl("/$movieId"))
            .retrieve()
            .bodyToMono(Map::class.java)
            .block()

        assertNotNull(response)
        assertEquals("The Fast and the Furious", response?.get("name"))
    }

    @Test
    fun `should handle movie not found`() {
        val movieId = "invalid-id"

        val responseStatus = webClient.get()
            .uri(baseUrl("/$movieId"))
            .exchangeToMono { response ->
                Mono.just(response.statusCode())
            }
            .block()

        assertEquals(404, responseStatus?.value())
    }
}
