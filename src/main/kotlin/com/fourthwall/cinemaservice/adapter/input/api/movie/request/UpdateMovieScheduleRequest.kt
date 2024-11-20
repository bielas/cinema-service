package com.fourthwall.cinemaservice.adapter.input.api.movie.request

import java.time.LocalDateTime

data class UpdateMovieScheduleRequest(
    val showtimes: List<ShowtimeRequest>
)

data class ShowtimeRequest(
    val time: LocalDateTime,
    val price: Double,
)

