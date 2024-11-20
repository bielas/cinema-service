package com.fourthwall.cinemaservice.adapter.input.api.movie.response

import java.time.LocalDate
import java.time.LocalDateTime

data class MovieResponse(
    val businessId: String,
    val name: String,
    val description: String,
    val releaseDate: LocalDate,
    val rating: Double,
    val externalRating: Double,
    val runtime: String
)

data class ShowtimeResponse(
    val time: LocalDateTime,
    val price: Double,
)
