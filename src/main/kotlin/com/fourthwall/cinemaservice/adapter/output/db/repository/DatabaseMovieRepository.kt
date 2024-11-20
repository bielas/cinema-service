package com.fourthwall.cinemaservice.adapter.output.db.repository

import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieRatingEntity
import com.fourthwall.cinemaservice.adapter.output.db.entity.ShowtimeEntity
import com.fourthwall.cinemaservice.adapter.output.db.jpa.MovieJPARepository
import com.fourthwall.cinemaservice.adapter.output.db.repository.mapper.MovieDatabaseMappers
import com.fourthwall.cinemaservice.domain.DomainException
import com.fourthwall.cinemaservice.domain.movie.Movie
import com.fourthwall.cinemaservice.domain.movie.MovieRating
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import com.fourthwall.cinemaservice.domain.movie.Showtime
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class DatabaseMovieRepository(
    private val movieJPARepository: MovieJPARepository,
    private val movieDatabaseMappers: MovieDatabaseMappers
) : MovieRepository {
    override fun get(movieId: String): Movie {
        val movie =
            movieJPARepository.findByBusinessId(movieId)
                ?: throw DomainException.DomainNotFoundException("Movie", movieId)
        return movieDatabaseMappers.toDomain(movie)
    }

    @Transactional
    override fun addRating(rating: MovieRating): Movie {
        val movie =
            movieJPARepository.findByBusinessId(rating.movieId)
                ?: throw DomainException.DomainNotFoundException("Movie", rating.movieId)

        movie.ratings.plus(
            MovieRatingEntity(
                userEmail = rating.userEmail,
                rating = rating.rating,
            )
        )
        return movieDatabaseMappers.toDomain(movie)
    }

    @Transactional
    override fun updateSchedule(movieId: String, userEmail: String, showtimes: List<Showtime>): Movie {
        val movie =
            movieJPARepository.findByBusinessId(movieId)
                ?: throw DomainException.DomainNotFoundException("Movie", movieId)

        showtimes.forEach {
            movie.showtimes.plus(
                ShowtimeEntity(
                    startTime = it.time,
                    price = it.price,
                    userEmail = userEmail
                )
            )
        }
        return movieDatabaseMappers.toDomain(movie)
    }
}
