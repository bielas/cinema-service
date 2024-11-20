package com.fourthwall.cinemaservice.domain.movie.command

import com.fourthwall.cinemaservice.domain.movie.Movie
import com.fourthwall.cinemaservice.domain.movie.MovieDetails
import com.fourthwall.cinemaservice.domain.movie.MovieRating
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import com.fourthwall.cinemaservice.domain.movie.Showtime
import com.fourthwall.cinemaservice.shared.exception.CommandValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.time.LocalDate
import java.time.LocalDateTime

class AddMovieRatingCommandHandlerTest {

    private lateinit var commandValidator: CommandValidator
    private lateinit var movieRepository: MovieRepository
    private lateinit var handler: AddMovieRatingCommandHandler

    @BeforeEach
    fun setup() {
        commandValidator = mock(CommandValidator::class.java)
        movieRepository = mock(MovieRepository::class.java)
        handler = AddMovieRatingCommandHandler(commandValidator, movieRepository)
    }

    @Test
    fun `should add movie rating and return updated movie`() {
        // Given
        val movieId = "movie-123"
        val userEmail = "user@example.com"
        val rating = 4.0
        val command = AddMovieRatingCommand(movieId, userEmail, rating)

        val existingMovie = Movie(
            businessId = movieId,
            details = MovieDetails(
                name = "Fast & Furious",
                description = "An action-packed movie.",
                releaseDate = LocalDate.now(),
                externalRating = 7.8,
                runtime = "120"
            ),
            rating = 4.5,
            showtimes = listOf(
                Showtime(LocalDateTime.now().plusHours(2), 15.0),
                Showtime(LocalDateTime.now().plusHours(4), 20.0)
            )
        )

        val updatedMovie = existingMovie.copy(rating = 4.6)

        val expectedMovieRating = MovieRating(
            movieId = movieId,
            userEmail = userEmail,
            rating = rating
        )

        `when`(movieRepository.addRating(expectedMovieRating)).thenReturn(updatedMovie)

        // When
        val result = handler.handle(command)

        // Then
        verify(commandValidator).validateCommand(command)
        verify(movieRepository).addRating(expectedMovieRating)
        assertEquals(updatedMovie, result)
    }

    @Test
    fun `should throw exception when validation fails`() {
        // Given
        val command = AddMovieRatingCommand("movie-123", "user@example.com", 4.0)
        doThrow(IllegalArgumentException("Validation failed")).`when`(commandValidator).validateCommand(command)

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            handler.handle(command)
        }
        assertEquals("Validation failed", exception.message)
    }
}
