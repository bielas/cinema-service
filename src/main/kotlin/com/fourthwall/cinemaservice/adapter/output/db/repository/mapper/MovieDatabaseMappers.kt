package com.fourthwall.cinemaservice.adapter.output.db.repository.mapper

import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieEntity
import com.fourthwall.cinemaservice.adapter.output.db.entity.MovieRatingEntity
import com.fourthwall.cinemaservice.adapter.output.db.entity.ShowtimeEntity
import com.fourthwall.cinemaservice.domain.movie.MovieBasic
import com.fourthwall.cinemaservice.domain.movie.Showtime

fun toDomain(entity: MovieEntity): MovieBasic {
    return MovieBasic(
        businessId = entity.businessId,
        rating = calculateAverageRating(entity.ratings),
        showtimes = mapShowtimes(entity.showtimes)
    )
}

private fun mapShowtimes(showtimeEntities: List<ShowtimeEntity>): List<Showtime> {
    return showtimeEntities.map {
        Showtime(
            time = it.startTime,
            price = it.price
        )
    }
}

private fun calculateAverageRating(ratings: List<MovieRatingEntity>): Double {
    return if (ratings.isNotEmpty()) {
        ratings.map { it.rating }.average()
    } else {
        0.0
    }
}
