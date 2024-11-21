package com.fourthwall.cinemaservice.domain.movie.query

import com.fourthwall.cinemaservice.domain.movie.MovieDetailsRepository
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import com.fourthwall.cinemaservice.domain.movie.Movie
import org.springframework.stereotype.Repository


@Repository
class MovieQuery(
    private val movieRepository: MovieRepository, private val movieDetailsRepository: MovieDetailsRepository
) {
    fun get(movieId: String): Movie {
        val movie = movieRepository.get(movieId)
        val movieDetails = movieDetailsRepository.get(movieId)
        return Movie(
            businessId = movieId, details = movieDetails, rating = movie.rating, showtimes = movie.showtimes
        )
    }
}
