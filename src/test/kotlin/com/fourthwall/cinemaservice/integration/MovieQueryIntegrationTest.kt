package com.fourthwall.cinemaservice.integration

import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieEntity
import com.fourthwall.cinemaservice.adapter.output.db.jpa.MovieJPARepository
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import com.fourthwall.cinemaservice.domain.movie.query.MovieQuery
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.okJson
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureWireMock(port = 0)
@Transactional
@ActiveProfiles("test")
class MovieQueryIntegrationTest @Autowired constructor(
    private val movieQuery: MovieQuery,
    private val movieJpaRepository: MovieJPARepository
) {

    @Test
    fun `should fetch movie details from IMDb and persist them`() {
        val movieId = "tt1234567"
        val imdbResponse = """
            {
                "Title": "Fast & Furious",
                "Year": "2009",
                "Released": "03 Apr 2009",
                "Runtime": "107 min",
                "Genre": "Action, Crime, Thriller",
                "Director": "Justin Lin",
                "Plot": "An action-packed movie about street racing.",
                "imdbRating": "7.1",
                "imdbVotes": "350,000"
            }
        """
        stubFor(
            get(urlPathEqualTo("/"))
                .withQueryParam("apikey", equalTo("test-api-key"))
                .withQueryParam("i", equalTo(movieId))
                .willReturn(okJson(imdbResponse))
        )


        val movieEntity = MovieEntity(
            businessId = movieId,
            showtimes = emptyList(),
            ratings = emptyList()
        )
        movieJpaRepository.save(movieEntity)

        val movie = movieQuery.get(movieId)

        assertEquals("Fast & Furious", movie.details.name)
        assertEquals("An action-packed movie about street racing.", movie.details.description)
        assertEquals(7.1, movie.details.externalRating)
        assertEquals("107 min", movie.details.runtime)
    }
}
