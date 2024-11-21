package com.fourthwall.cinemaservice.domain.movie.query

import com.fourthwall.cinemaservice.domain.movie.Movie
import com.fourthwall.cinemaservice.domain.movie.MovieMetadata
import com.fourthwall.cinemaservice.domain.movie.MovieDetailsRepository
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import org.springframework.stereotype.Repository

@Repository
class MovieQuery(
    private val movieRepository: MovieRepository,
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend fun get(movieId: String): Movie =
        movieRepository.get(movieId).let { movie ->
            Movie(
                metadata = MovieMetadata(
                    businessId = movieId,
                    rating = movie.rating,
                    showtimes = movie.showtimes
                ),
                details = movieDetailsRepository.get(movieId),
            )
        }
}
