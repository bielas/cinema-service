package com.fourthwall.cinemaservice.adapter.output.imdb

import com.fourthwall.cinemaservice.domain.movie.MovieDetails
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class ImdbMovieResponse(
    val Title: String,
    val Plot: String,
    val Released: String,
    val imdbRating: String,
    val Runtime: String
) {
    fun toDomain(): MovieDetails = MovieDetails(
        name = Title,
        description = Plot,
        releaseDate = LocalDate.parse(Released, DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)),
        externalRating = imdbRating.toDoubleOrNull() ?: 0.0,
        runtime = Runtime
    )
}
