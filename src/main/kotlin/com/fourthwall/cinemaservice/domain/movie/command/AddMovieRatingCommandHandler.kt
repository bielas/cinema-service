package com.fourthwall.cinemaservice.domain.movie.command

import com.cloudogu.cb.CommandHandler
import com.fourthwall.cinemaservice.domain.movie.MovieRating
import com.fourthwall.cinemaservice.domain.movie.MovieRepository
import com.fourthwall.cinemaservice.domain.movie.Movie
import com.fourthwall.cinemaservice.domain.movie.query.MovieQuery
import com.fourthwall.cinemaservice.shared.exception.CommandValidator
import org.springframework.stereotype.Component

@Component
class AddMovieRatingCommandHandler(
    private val commandValidator: CommandValidator,
    private val movieRepository: MovieRepository,
    private val movieQuery: MovieQuery
) : CommandHandler<Movie, AddMovieRatingCommand> {
    override fun handle(command: AddMovieRatingCommand): Movie {
        commandValidator.validateCommand(command)
        movieRepository.addRating(command.toMovieRating())
        return movieQuery.get(command.movieId)
    }
}

fun AddMovieRatingCommand.toMovieRating(): MovieRating {
    return MovieRating(
        movieId = movieId,
        userEmail = userEmail,
        rating = rating
    )
}
