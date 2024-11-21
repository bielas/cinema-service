package com.fourthwall.cinemaservice.domain.movie.command

import com.cloudogu.cb.CommandHandler
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import com.fourthwall.cinemaservice.domain.movie.Showtime
import com.fourthwall.cinemaservice.domain.movie.query.MovieQuery
import com.fourthwall.cinemaservice.shared.exception.CommandValidator
import org.springframework.stereotype.Component

@Component
class UpdateMovieScheduleCommandHandler(
    private val commandValidator: CommandValidator,
    private val movieRepository: MovieRepository,
    private val movieQuery: MovieQuery
) : CommandHandler<List<Showtime>, UpdateMovieScheduleCommand> {
    override fun handle(command: UpdateMovieScheduleCommand): List<Showtime> {
        commandValidator.validateCommand(command)
        movieRepository.updateSchedule(command.movieId, command.userEmail, command.showtimes)
        return movieQuery.get(command.movieId).showtimes
    }
}
