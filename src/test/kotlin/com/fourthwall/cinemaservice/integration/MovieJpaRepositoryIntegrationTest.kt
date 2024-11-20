package com.fourthwall.cinemaservice.integration

import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieEntity
import com.fourthwall.cinemaservice.adapter.output.db.jpa.MovieJPARepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MovieJpaRepositoryIntegrationTest @Autowired constructor(
    private val movieJpaRepository: MovieJPARepository,
) {

    @Test
    fun `should save and retrieve a movie`() {
        // Given
        val movieId = "tt1234567"
        val movie = MovieEntity(
            businessId = movieId,
            showtimes = emptyList()
        )
        movieJpaRepository.save(movie)

        // When
        val retrievedMovie = movieJpaRepository.findByBusinessId(movieId)

        // Then
        assertEquals(movie.businessId, retrievedMovie?.businessId)
        assertEquals(0, retrievedMovie?.showtimes?.size)
    }
}
