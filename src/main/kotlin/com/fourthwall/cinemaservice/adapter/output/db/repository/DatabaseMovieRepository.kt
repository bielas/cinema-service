package com.fourthwall.cinemaservice.adapter.output.db.repository

import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieRatingEntity
import com.fourthwall.cinemaservice.adapter.output.db.entity.ShowtimeEntity
import com.fourthwall.cinemaservice.adapter.output.db.jpa.MovieJPARepository
import com.fourthwall.cinemaservice.adapter.output.db.jpa.ShowtimeJPARepository
import com.fourthwall.cinemaservice.adapter.output.db.repository.mapper.toDomain
import com.fourthwall.cinemaservice.domain.DomainException
import com.fourthwall.cinemaservice.domain.movie.MovieBasic
import com.fourthwall.cinemaservice.domain.movie.MovieRating
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import com.fourthwall.cinemaservice.domain.movie.Showtime
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class DatabaseMovieRepository(
    private val movieJPARepository: MovieJPARepository,
    private val showtimeJPARepository: ShowtimeJPARepository,
) : MovieRepository {
    override fun get(movieId: String): MovieBasic {
        val movie =
            movieJPARepository.findByBusinessId(movieId)
                ?: throw DomainException.DomainNotFoundException("Movie", movieId)
        return toDomain(movie)
    }

    @Transactional
    override fun addRating(rating: MovieRating) {
        val movie = movieJPARepository.findByBusinessId(rating.movieId)
            ?: throw DomainException.DomainNotFoundException("Movie", rating.movieId)

        val updatedMovie = movie.withUpdatedRatings(
            movie.ratings + MovieRatingEntity(
                userEmail = rating.userEmail,
                rating = rating.rating,
                movie = movie
            )
        )

        movieJPARepository.save(updatedMovie)
    }

    @Transactional
    override fun updateSchedule(movieId: String, userEmail: String, showtimes: List<Showtime>) {
        val movie = movieJPARepository.findByBusinessId(movieId)
            ?: throw DomainException.DomainNotFoundException("Movie", movieId)

        showtimeJPARepository.deleteAllByMovieId(movie.id!!)

        val newShowtimeEntities = showtimes.map {
            ShowtimeEntity(
                startTime = it.time,
                price = it.price,
                userEmail = userEmail,
                movie = movie
            )
        }
        showtimeJPARepository.saveAll(newShowtimeEntities)
    }
}
