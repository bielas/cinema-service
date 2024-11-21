package com.fourthwall.cinemaservice.integration

import com.fourthwall.cinemaservice.domain.DomainException
import com.fourthwall.cinemaservice.domain.movie.command.AddMovieRatingCommand
import com.fourthwall.cinemaservice.domain.movie.command.AddMovieRatingCommandHandler
import com.fourthwall.cinemaservice.stubMovie
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class AddMovieRatingCommandHandlerIntegrationTest @Autowired constructor(
    private val handler: AddMovieRatingCommandHandler,
) : IntegrationTestBase() {

    @Test
    fun `should add rating to a movie`() {
        // Given
        val movieId = "tt0322259"
        val userEmail = "user@example.com"
        val rating = 3.0
        val command = AddMovieRatingCommand(movieId, userEmail, rating)
        stubMovie(movieId)

        // When
        val movie = handler.handle(command)

        // Then
        assertEquals(3.0, movie.metadata.rating)
    }

    @Test
    fun `should throw exception when rating a non-existing movie`() {
        // Given
        val movieId = "non-existing-id"
        val userEmail = "user@example.com"
        val rating = 5.0
        val command = AddMovieRatingCommand(movieId, userEmail, rating)

        // When / Then
        val exception = assertThrows<DomainException.DomainNotFoundException> {
            handler.handle(command)
        }
        assertEquals("Movie with id non-existing-id not found", exception.message)
    }

}

