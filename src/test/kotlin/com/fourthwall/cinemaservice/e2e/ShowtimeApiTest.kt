package com.fourthwall.cinemaservice.e2e.showtime

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShowtimeApiTest {

    @LocalServerPort
    private var port: Int = 0

    private val webClient: WebClient = WebClient.create()

    private fun baseUrl(endpoint: String): String = "http://localhost:$port/v1/movies$endpoint"

    @Test
    fun `should get movie showtimes`() {
        val movieId = "tt0232500"

        val response = webClient.get()
            .uri(baseUrl("/$movieId/times"))
            .retrieve()
            .bodyToMono(List::class.java)
            .block()

        assertNotNull(response)
        assertEquals(2, response?.size)
    }
}
