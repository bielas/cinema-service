package com.fourthwall.cinemaservice.domain.movie.command

import com.fourthwall.cinemaservice.domain.movie.Movie
import com.fourthwall.cinemaservice.domain.movie.MovieMetadata
import com.fourthwall.cinemaservice.domain.movie.MovieDetails
import com.fourthwall.cinemaservice.domain.movie.MovieRating
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import com.fourthwall.cinemaservice.domain.movie.Showtime
import com.fourthwall.cinemaservice.domain.movie.query.MovieQuery
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
    private lateinit var movieQuery: MovieQuery
    private lateinit var handler: AddMovieRatingCommandHandler

    @BeforeEach
    fun setup() {
        commandValidator = mock(CommandValidator::class.java)
        movieRepository = mock(MovieRepository::class.java)
        movieQuery = mock(MovieQuery::class.java)
        handler = AddMovieRatingCommandHandler(commandValidator, movieRepository, movieQuery)
    }

    @Test
    fun `should add movie rating and return updated movie`() {
        // Given
        val movieId = "movie-123"
        val userEmail = "user@example.com"
        val rating = 4.0
        val command = AddMovieRatingCommand(movieId, userEmail, rating)

        val existingMovie = Movie(
            metadata = MovieMetadata(
                businessId = movieId,
                rating = 4.5,
                showtimes = listOf(
                    Showtime(LocalDateTime.now().plusHours(2), 15.0),
                    Showtime(LocalDateTime.now().plusHours(4), 20.0)
                )
            ),
            details = MovieDetails(
                name = "Fast & Furious",
                description = "An action-packed movie.",
                releaseDate = LocalDate.now(),
                externalRating = 7.8,
                runtime = "120"
            ),
        )

        val expectedMovieRating = MovieRating(
            movieId = movieId,
            userEmail = userEmail,
            rating = rating
        )

        val updatedMovie = Movie(
            metadata = existingMovie.metadata.copy(rating = 4.6),
            details = existingMovie.details
        )

        doNothing().`when`(movieRepository).addRating(expectedMovieRating)
        `when`(movieQuery.get(movieId)).thenReturn(updatedMovie)

        // When
        val result = handler.handle(command)

        // Then
        verify(commandValidator).validateCommand(command)
        verify(movieRepository).addRating(expectedMovieRating)
        verify(movieQuery).get(movieId)
        assertEquals(updatedMovie.metadata.businessId, result.metadata.businessId)
        assertEquals(4.6, result.metadata.rating)
        assertEquals(existingMovie.details.name, result.details.name)
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

    @Test
    fun `should validate command when adding movie rating`() {
        // Given
        val movieId = "tt0232500"
        val userEmail = "user@example.com"
        val rating = 4.0
        val command = AddMovieRatingCommand(movieId, userEmail, rating)

        val existingMovie = Movie(
            details = MovieDetails(
                name = "Fast & Furious",
                description = "An action-packed movie.",
                releaseDate = LocalDate.now(),
                externalRating = 7.8,
                runtime = "120"
            ),
            metadata = MovieMetadata(
                businessId = movieId,
                rating = 4.5,
                showtimes = emptyList()
            ),
        )

        `when`(movieQuery.get(movieId)).thenReturn(existingMovie)

        // When
        handler.handle(command)

        // Then
        verify(commandValidator).validateCommand(command)
    }

    @Test
    fun `should throw exception when AddMovieRatingCommand validation fails`() {
        // Given
        val movieId = "tt0232500"
        val userEmail = "invalid-email"
        val rating = 6.0
        val command = AddMovieRatingCommand(movieId, userEmail, rating)

        doThrow(IllegalArgumentException("Validation failed"))
            .`when`(commandValidator).validateCommand(command)

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            handler.handle(command)
        }
        assertEquals("Validation failed", exception.message)
    }
}
