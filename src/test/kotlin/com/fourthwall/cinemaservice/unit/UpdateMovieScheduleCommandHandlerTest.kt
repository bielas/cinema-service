package com.fourthwall.cinemaservice.domain.movie.command

import com.fourthwall.cinemaservice.domain.movie.Movie
import com.fourthwall.cinemaservice.domain.movie.MovieDetails
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

class UpdateMovieScheduleCommandHandlerTest {

    private lateinit var commandValidator: CommandValidator
    private lateinit var movieRepository: MovieRepository
    private lateinit var handler: UpdateMovieScheduleCommandHandler

    @BeforeEach
    fun setup() {
        commandValidator = mock(CommandValidator::class.java)
        movieRepository = mock(MovieRepository::class.java)
        handler = UpdateMovieScheduleCommandHandler(commandValidator, movieRepository)
    }

    @Test
    fun `should update movie schedule and return updated showtimes`() {
        // Given
        val movieId = "movie-123"
        val userEmail = "user@example.com"
        val newShowtimes = listOf(
            Showtime(LocalDateTime.now().plusDays(1), 12.0),
            Showtime(LocalDateTime.now().plusDays(2), 15.0)
        )
        val command = UpdateMovieScheduleCommand(movieId, userEmail, newShowtimes)

        val movie = Movie(
            businessId = movieId,
            details = MovieDetails(
                name = "Fast & Furious",
                description = "An action-packed movie.",
                releaseDate = LocalDate.now(),
                externalRating = 7.5,
                runtime = "120"
            ),
            rating = 4.3,
            showtimes = emptyList()
        )

        `when`(movieRepository.get(movieId)).thenReturn(movie)
        `when`(movieRepository.updateSchedule(movieId, userEmail, newShowtimes)).thenReturn(movie.copy(showtimes = newShowtimes))

        // When
        val updatedShowtimes = handler.handle(command)

        // Then
        verify(commandValidator).validateCommand(command)
        verify(movieRepository).updateSchedule(movieId, userEmail, newShowtimes)
        assertEquals(newShowtimes, updatedShowtimes)
    }

    @Test
    fun `should throw exception when validation fails`() {
        // Given
        val command = mock(UpdateMovieScheduleCommand::class.java)
        doThrow(IllegalArgumentException("Validation failed")).`when`(commandValidator).validateCommand(command)

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            handler.handle(command)
        }
        assertEquals("Validation failed", exception.message)
    }

    @Test
    fun `should throw exception when UpdateMovieScheduleCommand validation fails`() {
        // Given
        val movieId = "tt0232500"
        val userEmail = "invalid-email"
        val newShowtimes = listOf(
            Showtime(LocalDateTime.now().plusDays(1), 12.0)
        )
        val command = UpdateMovieScheduleCommand(movieId, userEmail, newShowtimes)

        doThrow(IllegalArgumentException("Validation failed"))
            .`when`(commandValidator).validateCommand(command)

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            handler.handle(command)
        }
        assertEquals("Validation failed", exception.message)
    }
}
