package com.fourthwall.cinemaservice.adapter.input.api.movie

import com.cloudogu.cb.CommandBus
import com.fourthwall.cinemaservice.adapter.input.api.movie.request.AddMovieRatingRequest
import com.fourthwall.cinemaservice.adapter.input.api.movie.response.MovieResponse
import com.fourthwall.cinemaservice.adapter.input.api.movie.response.ShowtimeResponse
import com.fourthwall.cinemaservice.configuration.security.AuthenticationFacade
import com.fourthwall.cinemaservice.domain.movie.command.AddMovieRatingCommand
import com.fourthwall.cinemaservice.domain.movie.query.MovieQuery
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController(
    private val authenticationFacade: AuthenticationFacade,
    private val movieQuery: MovieQuery,
    private val commandBus: CommandBus
) : MovieApi {

    override fun getMovie(movieId: String): ResponseEntity<MovieResponse> =
        movieQuery.get(movieId)
            .toMovieResponse()
            .toResponseEntity()

    override fun getMovieShowtimes(movieId: String): ResponseEntity<List<ShowtimeResponse>> =
        movieQuery.get(movieId)
            .metadata.showtimes
            .toShowtimeResponse()
            .toResponseEntity()

    override fun addMovieRating(movieId: String, body: AddMovieRatingRequest): ResponseEntity<MovieResponse> =
        authenticationFacade.getUserEmail()
            .let { userEmail -> body.toCommand(userEmail, movieId) }
            .let { commandBus.execute(it) }
            .toMovieResponse()
            .toResponseEntity(HttpStatus.CREATED)
}

private fun MovieResponse.toResponseEntity(status: HttpStatus = HttpStatus.OK): ResponseEntity<MovieResponse> =
    ResponseEntity.status(status).body(this)

private fun List<ShowtimeResponse>.toResponseEntity(status: HttpStatus = HttpStatus.OK): ResponseEntity<List<ShowtimeResponse>> =
    ResponseEntity.status(status).body(this)

private fun AddMovieRatingRequest.toCommand(email: String, movieId: String): AddMovieRatingCommand =
    AddMovieRatingCommand(movieId = movieId, userEmail = email, rating = rating)
