package com.fourthwall.cinemaservice.e2e

import com.fourthwall.cinemaservice.adapter.input.api.movie.request.AddMovieRatingRequest
import com.fourthwall.cinemaservice.integration.IntegrationTestBase
import com.fourthwall.cinemaservice.stubMovie
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.web.reactive.function.client.WebClient

class MovieReviewApiTest : IntegrationTestBase() {

    @LocalServerPort
    private var port: Int = 0

    private val webClient: WebClient = WebClient.create()

    private fun baseUrl(endpoint: String): String = "http://localhost:$port/v1/movies$endpoint"

    @Test
    fun `should add a movie rating`() {
        val movieId = "tt0232500"
        stubMovie(movieId)

        val request = AddMovieRatingRequest(rating = 3.0)

        val response = webClient.post()
            .uri(baseUrl("/$movieId/rating"))
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Map::class.java)
            .block()

        assertNotNull(response)
        assertEquals(4.0, response?.get("rating"))
    }
}
