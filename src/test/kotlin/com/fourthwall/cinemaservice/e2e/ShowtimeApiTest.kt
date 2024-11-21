package com.fourthwall.cinemaservice.e2e.showtime

import com.fourthwall.cinemaservice.integration.IntegrationTestBase
import com.fourthwall.cinemaservice.stubMovie
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.web.reactive.function.client.WebClient

class ShowtimeApiTest : IntegrationTestBase() {

    @LocalServerPort
    private var port: Int = 0

    private val webClient: WebClient = WebClient.create()

    private fun baseUrl(endpoint: String): String = "http://localhost:$port/v1/movies$endpoint"

    @Test
    fun `should get movie showtimes`() {
        val movieId = "tt0232500"
        stubMovie(movieId)

        val response = webClient.get()
            .uri(baseUrl("/$movieId/times"))
            .retrieve()
            .bodyToMono(List::class.java)
            .block()

        assertNotNull(response)
        assertEquals(2, response?.size)
    }
}
