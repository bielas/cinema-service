package com.fourthwall.cinemaservice.integration

import com.fourthwall.cinemaservice.domain.DomainException
import com.fourthwall.cinemaservice.domain.movie.command.AddMovieRatingCommand
import com.fourthwall.cinemaservice.domain.movie.command.AddMovieRatingCommandHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class AddMovieRatingCommandHandlerIntegrationTest @Autowired constructor(
    private val handler: AddMovieRatingCommandHandler,
) {

    @Test
    fun `should add rating to a movie`() {
        // Given
        val movieId = "tt0232500"
        val userEmail = "user@example.com"
        val rating = 3.0
        val command = AddMovieRatingCommand(movieId, userEmail, rating)

        // When
        val movie = handler.handle(command)

        // Then
        assertEquals(4.0, movie.rating)
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

