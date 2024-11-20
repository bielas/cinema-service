package com.fourthwall.cinemaservice.e2e.showtime

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
class ShowtimeApiTest @Autowired constructor(
    private val restTemplate: TestRestTemplate
) {

    @LocalServerPort
    private var port: Int = 0

    private fun baseUrl(endpoint: String): String = "http://localhost:$port/v1/movies$endpoint"

    @Test
    fun `should get movie showtimes`() {
        val movieId = "tt0232500"
        val response: ResponseEntity<List<*>> = restTemplate.getForEntity(baseUrl("/$movieId/times"), List::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(2, response.body?.size)
    }
}
