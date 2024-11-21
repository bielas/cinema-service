package com.fourthwall.cinemaservice.unit

import com.fourthwall.cinemaservice.domain.movie.MovieBasic
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import com.fourthwall.cinemaservice.domain.movie.Showtime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime

class MovieRepositoryTest {

    private lateinit var movieRepository: MovieRepository

    @BeforeEach
    fun setup() {
        movieRepository = mock(MovieRepository::class.java)
    }

    @Test
    fun `should fetch movie details by ID`() {
        // Given
        val movieId = "tt1234567"
        val movieBasic = MovieBasic(
            businessId = movieId,
            rating = 4.5,
            showtimes = listOf(
                Showtime(LocalDateTime.now().plusHours(2), 15.0),
                Showtime(LocalDateTime.now().plusHours(4), 20.0)
            )
        )

        `when`(movieRepository.get(movieId)).thenReturn(movieBasic)

        // When
        val result = movieRepository.get(movieId)

        // Then
        assertEquals(movieBasic, result)
    }
}
