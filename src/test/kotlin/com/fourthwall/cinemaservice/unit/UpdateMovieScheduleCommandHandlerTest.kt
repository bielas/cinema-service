import com.fourthwall.cinemaservice.domain.DomainException
import com.fourthwall.cinemaservice.domain.movie.Movie
import com.fourthwall.cinemaservice.domain.movie.MovieMetadata
import com.fourthwall.cinemaservice.domain.movie.MovieDetails
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import com.fourthwall.cinemaservice.domain.movie.Showtime
import com.fourthwall.cinemaservice.domain.movie.command.UpdateMovieScheduleCommand
import com.fourthwall.cinemaservice.domain.movie.command.UpdateMovieScheduleCommandHandler
import com.fourthwall.cinemaservice.domain.movie.query.MovieQuery
import com.fourthwall.cinemaservice.shared.exception.CommandValidator
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.time.LocalDate
import java.time.LocalDateTime

class UpdateMovieScheduleCommandHandlerTest {

    private lateinit var commandValidator: CommandValidator
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieQuery: MovieQuery
    private lateinit var handler: UpdateMovieScheduleCommandHandler

    @BeforeEach
    fun setup() {
        commandValidator = mock(CommandValidator::class.java)
        movieRepository = mock(MovieRepository::class.java)
        movieQuery = mock(MovieQuery::class.java)
        handler = UpdateMovieScheduleCommandHandler(commandValidator, movieRepository, movieQuery)
    }

    @Test
    fun `should update showtimes for a movie`() = runBlocking {
        // Given
        val movieId = "tt0232500"
        val userEmail = "user@example.com"
        val newShowtimes = listOf(
            Showtime(LocalDateTime.now().plusDays(1), 12.0),
            Showtime(LocalDateTime.now().plusDays(2), 15.0)
        )
        val command = UpdateMovieScheduleCommand(movieId, userEmail, newShowtimes)

        val updatedMovie = Movie(
            metadata = MovieMetadata(
                businessId = movieId,
                rating = 4.5,
                showtimes = newShowtimes
            ),
            details = MovieDetails(
                name = "Fast & Furious",
                description = "An action-packed movie.",
                releaseDate = LocalDate.now(),
                externalRating = 7.8,
                runtime = "120"
            ),
        )

        doNothing().`when`(commandValidator).validateCommand(command)
        `when`(movieQuery.get(movieId)).thenReturn(updatedMovie)

        // When
        val result = handler.handle(command)

        // Then
        verify(commandValidator).validateCommand(command)
        verify(movieRepository).updateSchedule(movieId, userEmail, newShowtimes)
        assertEquals(newShowtimes, result)
    }

    @Test
    fun `should throw exception when validation fails`() = runBlocking {
        // Given
        val command = UpdateMovieScheduleCommand("tt0232500", "user@example.com", emptyList())
        doThrow(IllegalArgumentException("Validation failed")).`when`(commandValidator).validateCommand(command)

        // When / Then
        val exception = assertThrows<IllegalArgumentException> {
            handler.handle(command)
        }
        assertEquals("Validation failed", exception.message)
    }

    @Test
    fun `should replace existing showtimes with new ones`() = runBlocking {
        // Given
        val movieId = "tt0232500"
        val userEmail = "user@example.com"
        val newShowtimes = listOf(
            Showtime(LocalDateTime.now().plusDays(3), 18.0),
            Showtime(LocalDateTime.now().plusDays(4), 22.0)
        )
        val command = UpdateMovieScheduleCommand(movieId, userEmail, newShowtimes)

        val updatedMovie = Movie(
            metadata = MovieMetadata(
                businessId = movieId,
                rating = 4.5,
                showtimes = newShowtimes
            ),
            details = MovieDetails(
                name = "Fast & Furious",
                description = "An action-packed movie.",
                releaseDate = LocalDate.now(),
                externalRating = 7.8,
                runtime = "120"
            )
        )

        doNothing().`when`(commandValidator).validateCommand(command)
        `when`(movieQuery.get(movieId)).thenReturn(updatedMovie)

        // When
        val result = handler.handle(command)

        // Then
        verify(commandValidator).validateCommand(command)
        verify(movieRepository).updateSchedule(movieId, userEmail, newShowtimes)
        assertEquals(newShowtimes, result)
    }

    @Test
    fun `should remove all showtimes when empty list provided`() = runBlocking {
        // Given
        val movieId = "tt0232500"
        val userEmail = "user@example.com"
        val command = UpdateMovieScheduleCommand(movieId, userEmail, emptyList())

        val updatedMovie = Movie(
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

        doNothing().`when`(commandValidator).validateCommand(command)
        `when`(movieQuery.get(movieId)).thenReturn(updatedMovie)

        // When
        val result = handler.handle(command)

        // Then
        verify(commandValidator).validateCommand(command)
        verify(movieRepository).updateSchedule(movieId, userEmail, emptyList())
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should throw exception when movie does not exist`() = runBlocking {
        // Given
        val movieId = "non-existing-id"
        val userEmail = "user@example.com"
        val newShowtimes = listOf(
            Showtime(LocalDateTime.now().plusDays(1), 20.0)
        )
        val command = UpdateMovieScheduleCommand(movieId, userEmail, newShowtimes)

        doNothing().`when`(commandValidator).validateCommand(command)
        doThrow(DomainException.DomainNotFoundException("Movie", movieId))
            .`when`(movieRepository).updateSchedule(movieId, userEmail, newShowtimes)

        // When / Then
        val exception = assertThrows<DomainException.DomainNotFoundException> {
            handler.handle(command)
        }
        assertEquals("Movie with id non-existing-id not found", exception.message)
    }
}
