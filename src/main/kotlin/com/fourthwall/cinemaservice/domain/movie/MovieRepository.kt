package com.fourthwall.cinemaservice.domain.movie

interface MovieRepository {
    fun get(movieId: String): Movie
    fun addRating(rating: MovieRating)
    fun updateSchedule(movieId: String, userEmail: String, showtimes: List<Showtime>)
}
