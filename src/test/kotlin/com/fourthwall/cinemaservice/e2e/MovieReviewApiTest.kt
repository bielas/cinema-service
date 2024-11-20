package com.fourthwall.cinemaservice.e2e.review

import com.fourthwall.cinemaservice.adapter.input.api.movie.request.AddMovieRatingRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieReviewApiTest @Autowired constructor(
    private val restTemplate: TestRestTemplate
) {

    @LocalServerPort
    private var port: Int = 0

    private fun baseUrl(endpoint: String): String = "http://localhost:$port/v1/movies$endpoint"

    @Test
    fun `should add a movie rating`() {
        val movieId = "tt0232500"
        val request = AddMovieRatingRequest(rating = 3.0)
        val response: ResponseEntity<Map<*, *>> =
            restTemplate.postForEntity(baseUrl("/$movieId/rating"), request, Map::class.java)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(4.0, response.body?.get("rating"))
    }
}
