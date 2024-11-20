package com.fourthwall.cinemaservice.integration

import com.fourthwall.cinemaservice.domain.DomainException
import com.fourthwall.cinemaservice.domain.movie.Showtime
import com.fourthwall.cinemaservice.domain.movie.command.UpdateMovieScheduleCommand
import com.fourthwall.cinemaservice.domain.movie.command.UpdateMovieScheduleCommandHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@Transactional
class UpdateMovieScheduleCommandHandlerIntegrationTest @Autowired constructor(
    private val handler: UpdateMovieScheduleCommandHandler,
) {
    @Test
    fun `should update showtimes for a movie`() {
        // Given
        val movieId = "tt0232500"
        val userEmail = "user@example.com"

        val newShowtimes = listOf(
            Showtime(LocalDateTime.now().plusDays(1), 12.0),
            Showtime(LocalDateTime.now().plusDays(2), 15.0)
        )
        val command = UpdateMovieScheduleCommand(movieId, userEmail, newShowtimes)

        // When
        val updatedShowtimes = handler.handle(command)

        // Then
        assertEquals(2, updatedShowtimes.size)
        assertEquals(newShowtimes, updatedShowtimes)
    }


    @Test
    fun `should add new showtimes when none exist`() {
        // Given
        val movieId = "tt0322259"
        val userEmail = "user@example.com"
        val newShowtimes = listOf(
            Showtime(LocalDateTime.now().plusDays(1), 20.0),
            Showtime(LocalDateTime.now().plusDays(2), 25.0)
        )
        val command = UpdateMovieScheduleCommand(movieId, userEmail, newShowtimes)

        // When
        val updatedShowtimes = handler.handle(command)

        // Then
        assertEquals(2, updatedShowtimes.size)
        assertEquals(newShowtimes, updatedShowtimes)
    }

    @Test
    fun `should replace existing showtimes with new ones`() {
        // Given
        val movieId = "tt0232500"
        val userEmail = "user@example.com"
        val newShowtimes = listOf(
            Showtime(LocalDateTime.now().plusDays(3), 18.0),
            Showtime(LocalDateTime.now().plusDays(4), 22.0)
        )
        val command = UpdateMovieScheduleCommand(movieId, userEmail, newShowtimes)

        // When
        val updatedShowtimes = handler.handle(command)

        // Then
        assertEquals(2, updatedShowtimes.size)
        assertEquals(newShowtimes, updatedShowtimes)
    }

    @Test
    fun `should remove all showtimes when empty list provided`() {
        // Given
        val movieId = "tt0232500"
        val userEmail = "user@example.com"
        val command = UpdateMovieScheduleCommand(movieId, userEmail, emptyList())

        // When
        val updatedShowtimes = handler.handle(command)

        // Then
        assertTrue(updatedShowtimes.isEmpty())
    }

    @Test
    fun `should throw exception when movie does not exist`() {
        // Given
        val movieId = "non-existing-id"
        val userEmail = "user@example.com"
        val newShowtimes = listOf(
            Showtime(LocalDateTime.now().plusDays(1), 20.0)
        )
        val command = UpdateMovieScheduleCommand(movieId, userEmail, newShowtimes)

        // When / Then
        val exception = assertThrows<DomainException.DomainNotFoundException> {
            handler.handle(command)
        }
        assertEquals("Movie with id non-existing-id not found", exception.message)
    }

    @Test
    fun `should handle duplicate showtimes with different prices`() {
        // Given
        val movieId = "tt0232500"
        val userEmail = "user@example.com"
        val newShowtimes = listOf(
            Showtime(LocalDateTime.now().plusDays(1), 15.0),
            Showtime(LocalDateTime.now().plusDays(1), 20.0)
        )
        val command = UpdateMovieScheduleCommand(movieId, userEmail, newShowtimes)

        // When
        val updatedShowtimes = handler.handle(command)

        // Then
        assertEquals(2, updatedShowtimes.size)
        assertEquals(newShowtimes, updatedShowtimes)
    }
}
