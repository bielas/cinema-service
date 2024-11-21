package com.fourthwall.cinemaservice.adapter.output.db.repository

import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieRatingEntity
import com.fourthwall.cinemaservice.adapter.output.db.entity.ShowtimeEntity
import com.fourthwall.cinemaservice.adapter.output.db.jpa.MovieJPARepository
import com.fourthwall.cinemaservice.adapter.output.db.jpa.ShowtimeJPARepository
import com.fourthwall.cinemaservice.adapter.output.db.repository.mapper.toDomain
import com.fourthwall.cinemaservice.domain.DomainException
import com.fourthwall.cinemaservice.domain.movie.MovieMetadata
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

    override fun get(movieId: String): MovieMetadata =
        movieJPARepository.findByBusinessId(movieId)
            ?.let { toDomain(it) }
            ?: throw DomainException.DomainNotFoundException("Movie", movieId)

    @Transactional
    override fun addRating(rating: MovieRating) {
        movieJPARepository.findByBusinessId(rating.movieId)
            ?.run {
                val updatedRatings = ratings + MovieRatingEntity(
                    userEmail = rating.userEmail,
                    rating = rating.rating,
                    movie = this
                )
                withUpdatedRatings(updatedRatings).apply {
                    movieJPARepository.save(this)
                }
            }
            ?: throw DomainException.DomainNotFoundException("Movie", rating.movieId)
    }

    @Transactional
    override fun updateSchedule(movieId: String, userEmail: String, showtimes: List<Showtime>) {
        movieJPARepository.findByBusinessId(movieId)
            ?.also { movie ->
                showtimeJPARepository.deleteAllByMovieId(movie.id!!)
                showtimes.map {
                    ShowtimeEntity(
                        startTime = it.time,
                        price = it.price,
                        userEmail = userEmail,
                        movie = movie
                    )
                }.also { newShowtimeEntities ->
                    showtimeJPARepository.saveAll(newShowtimeEntities)
                }
            }
            ?: throw DomainException.DomainNotFoundException("Movie", movieId)
    }
}
