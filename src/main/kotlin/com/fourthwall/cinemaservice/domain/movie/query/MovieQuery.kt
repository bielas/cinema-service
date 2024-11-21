package com.fourthwall.cinemaservice.domain.movie.query

import com.fourthwall.cinemaservice.domain.movie.Movie
import com.fourthwall.cinemaservice.domain.movie.MovieDetailsRepository
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import org.springframework.stereotype.Repository

@Repository
class MovieQuery(
    private val movieRepository: MovieRepository,
    private val movieDetailsRepository: MovieDetailsRepository
) {
    fun get(movieId: String): Movie =
        movieRepository.get(movieId).let { movie ->
            Movie(
                businessId = movieId,
                details = movieDetailsRepository.get(movieId),
                rating = movie.rating,
                showtimes = movie.showtimes
            )
        }
}
