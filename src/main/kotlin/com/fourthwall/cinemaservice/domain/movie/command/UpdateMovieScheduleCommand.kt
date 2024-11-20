package com.fourthwall.cinemaservice.domain.movie.command

import com.cloudogu.cb.Command
import com.fourthwall.cinemaservice.domain.movie.Showtime

class UpdateMovieScheduleCommand(
    val movieId: String,
    val userEmail: String,
    val showtimes: List<Showtime>
) : Command<List<Showtime>>
