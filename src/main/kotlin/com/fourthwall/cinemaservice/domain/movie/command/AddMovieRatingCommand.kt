package com.fourthwall.cinemaservice.domain.movie.command

import com.cloudogu.cb.Command
import com.fourthwall.cinemaservice.domain.movie.Movie
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

class AddMovieRatingCommand(
    val movieId: String,

    @field:jakarta.validation.constraints.Email(message = "Invalid email format")
    @field:NotBlank(message = "Email cannot be blank")
    val userEmail: String,

    @field:Min(1, message = "Rating must be at least 1")
    @field:Max(5, message = "Rating must be at most 5")
    val rating: Double
) : Command<Movie>
