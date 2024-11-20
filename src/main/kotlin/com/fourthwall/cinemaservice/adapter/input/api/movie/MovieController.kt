package com.fourthwall.cinemaservice.adapter.input.api.movie

import com.cloudogu.cb.CommandBus
import com.fourthwall.cinemaservice.adapter.input.api.movie.request.AddMovieRatingRequest
import com.fourthwall.cinemaservice.adapter.input.api.movie.response.MovieResponse
import com.fourthwall.cinemaservice.adapter.input.api.movie.response.ShowtimeResponse
import com.fourthwall.cinemaservice.configuration.security.AuthenticationFacade
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import com.fourthwall.cinemaservice.domain.movie.command.AddMovieRatingCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController(
    private val authenticationFacade: AuthenticationFacade,
    private val movieRepository: MovieRepository,
    private val commandBus: CommandBus
) : MovieApi {
    override fun getMovie(movieId: String): ResponseEntity<MovieResponse> =
        movieRepository.get(movieId).let { ResponseEntity.ok(it.toMovieResponse()) }

    override fun getMovieShowtimes(movieId: String): ResponseEntity<List<ShowtimeResponse>> =
        movieRepository.get(movieId).showtimes.toShowtimeResponse().let { ResponseEntity.ok(it) }

    override fun addMovieRating(movieId: String, body: AddMovieRatingRequest): ResponseEntity<MovieResponse> {
        val userEmail = authenticationFacade.getUserEmail()
        val command = body.toCommand(userEmail, movieId)
        return ResponseEntity.status(HttpStatus.CREATED).body(commandBus.execute(command).toMovieResponse())
    }
}

private fun AddMovieRatingRequest.toCommand(
    email: String, movieId: String
) = AddMovieRatingCommand(
    movieId = movieId, userEmail = email, rating = rating
)
