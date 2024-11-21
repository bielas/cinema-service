package com.fourthwall.cinemaservice.adapter.input.api.movie

import com.fourthwall.cinemaservice.adapter.input.api.movie.response.MovieResponse
import com.fourthwall.cinemaservice.adapter.input.api.movie.response.ShowtimeResponse
import com.fourthwall.cinemaservice.domain.movie.Movie
import com.fourthwall.cinemaservice.domain.movie.Showtime

fun Movie.toMovieResponse(): MovieResponse = MovieResponse(
    businessId = metadata.businessId,
    name = details.name,
    description = details.description,
    releaseDate = details.releaseDate,
    rating = metadata.rating,
    externalRating = details.externalRating,
    runtime = details.runtime
)

fun List<Showtime>.toShowtimeResponse(): List<ShowtimeResponse> {
    return this.map { it.toShowtimeResponse() }
}

private fun Showtime.toShowtimeResponse(): ShowtimeResponse = ShowtimeResponse(
    time = time,
    price = price
)


