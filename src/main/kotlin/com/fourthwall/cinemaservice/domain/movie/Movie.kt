package com.fourthwall.cinemaservice.domain.movie

import java.time.LocalDate
import java.time.LocalDateTime

data class MovieBasic(
    val businessId: String,
    val rating: Double,
    val showtimes: List<Showtime>
)

data class Movie(
    val businessId: String,
    val details: MovieDetails,
    val rating: Double,
    val showtimes: List<Showtime>
)

data class MovieDetails(
    val name: String,
    val description: String,
    val releaseDate: LocalDate,
    val externalRating: Double,
    val runtime: String,
)

data class Showtime(
    val time: LocalDateTime,
    val price: Double,
)

data class MovieRating(
    val movieId: String,
    val userEmail: String,
    val rating: Double
)
