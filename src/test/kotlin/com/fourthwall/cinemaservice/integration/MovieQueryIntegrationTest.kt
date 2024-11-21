package com.fourthwall.cinemaservice.integration

import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieEntity
import com.fourthwall.cinemaservice.adapter.output.db.jpa.MovieJPARepository
import com.fourthwall.cinemaservice.domain.movie.query.MovieQuery
import com.fourthwall.cinemaservice.stubMovie
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class MovieQueryIntegrationTest @Autowired constructor(
    private val movieQuery: MovieQuery,
    private val movieJpaRepository: MovieJPARepository
) : IntegrationTestBase() {

    @Test
    fun `should fetch movie details from IMDb and persist them`() = runBlocking {
        val movieId = "tt1234567"
        stubMovie(movieId)

        val movieEntity = MovieEntity(
            businessId = movieId,
            showtimes = emptyList(),
            ratings = emptyList()
        )
        movieJpaRepository.save(movieEntity)

        val movie = movieQuery.get(movieId)

        assertEquals("The Fast and the Furious", movie.details.name)
        assertEquals("Los Angeles police officer Brian O'Conner must decide where his loyalty really lies when he becomes enamored with the street racing world he has been sent undercover to destroy.", movie.details.description)
        assertEquals(6.8, movie.details.externalRating)
        assertEquals("106 min", movie.details.runtime)
    }
}
