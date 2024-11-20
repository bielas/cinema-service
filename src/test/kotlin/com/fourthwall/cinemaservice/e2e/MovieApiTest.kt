package com.fourthwall.cinemaservice.e2e.movie

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieApiTest @Autowired constructor(
    private val restTemplate: TestRestTemplate
) {

    @LocalServerPort
    private var port: Int = 0

    private fun baseUrl(endpoint: String): String = "http://localhost:$port/v1/movies$endpoint"

    @Test
    fun `should get movie by ID`() {
        val movieId = "tt0232500"
        val response = restTemplate.getForEntity(baseUrl("/$movieId"), Map::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals("The Fast and the Furious", response.body?.get("name"))
    }

    @Test
    fun `should handle movie not found`() {
        val movieId = "invalid-id"
        val response = restTemplate.getForEntity(baseUrl("/$movieId"), Map::class.java)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }
}
