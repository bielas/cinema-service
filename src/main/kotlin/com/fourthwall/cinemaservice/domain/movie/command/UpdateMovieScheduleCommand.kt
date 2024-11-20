package com.fourthwall.cinemaservice.domain.movie.command

import com.cloudogu.cb.Command
import com.fourthwall.cinemaservice.domain.movie.Showtime
import jakarta.validation.constraints.NotBlank

class UpdateMovieScheduleCommand(
    val movieId: String,
    @field:jakarta.validation.constraints.Email(message = "Invalid email format")
    @field:NotBlank(message = "Email cannot be blank")
    val userEmail: String,
    val showtimes: List<Showtime>
) : Command<List<Showtime>>
