package com.fourthwall.cinemaservice.adapter.input.api.movie

import com.cloudogu.cb.CommandBus
import com.fourthwall.cinemaservice.adapter.input.api.movie.request.UpdateMovieScheduleRequest
import com.fourthwall.cinemaservice.adapter.input.api.movie.response.ShowtimeResponse
import com.fourthwall.cinemaservice.configuration.security.AuthenticationFacade
import com.fourthwall.cinemaservice.domain.movie.Showtime
import com.fourthwall.cinemaservice.domain.movie.command.UpdateMovieScheduleCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class SelfMovieController(
    private val commandBus: CommandBus,
    private val authenticationFacade: AuthenticationFacade,
) : SelfMovieApi {

    override fun updateMovieSchedule(
        movieId: String,
        body: UpdateMovieScheduleRequest
    ): ResponseEntity<List<ShowtimeResponse>> =
        authenticationFacade.getUserEmail()
            .let { userEmail -> body.toCommand(userEmail, movieId) }
            .let { commandBus.execute(it) }
            .toShowtimeResponse()
            .toResponseEntity(HttpStatus.NO_CONTENT)
}

private fun List<ShowtimeResponse>.toResponseEntity(status: HttpStatus = HttpStatus.OK): ResponseEntity<List<ShowtimeResponse>> =
    ResponseEntity.status(status).body(this)

private fun UpdateMovieScheduleRequest.toCommand(
    email: String,
    movieId: String
): UpdateMovieScheduleCommand =
    UpdateMovieScheduleCommand(
        movieId = movieId,
        userEmail = email,
        showtimes = showtimes.map { Showtime(it.time, it.price) }
    )
