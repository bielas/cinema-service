package com.fourthwall.cinemaservice.adapter.input.api.movie

import com.fourthwall.cinemaservice.adapter.input.api.movie.request.AddMovieRatingRequest
import com.fourthwall.cinemaservice.adapter.input.api.movie.response.MovieResponse
import com.fourthwall.cinemaservice.adapter.input.api.movie.response.ShowtimeResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Validated
@Tag(name = "Movie operations")
@RequestMapping("/v1/movies")
interface MovieApi {
    @Operation(summary = "Get movie by id")
    @GetMapping(path = ["/{movieId}"])
    @ApiResponse(
        responseCode = "400",
        description = "Incorrect input format",
        content = [Content(schema = Schema(implementation = ProblemDetail::class))],
    )
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "200", description = "Successfully get movie by id")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getMovie(@PathVariable movieId: String): ResponseEntity<MovieResponse>

    @Operation(summary = "Get movie times by movie id")
    @GetMapping(path = ["/{movieId}/times"])
    @ApiResponse(
        responseCode = "400",
        description = "Incorrect input format",
        content = [Content(schema = Schema(implementation = ProblemDetail::class))],
    )
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "200", description = "Successfully get movie times by movie id")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getMovieShowtimes(@PathVariable movieId: String): ResponseEntity<List<ShowtimeResponse>>

    @Operation(summary = "Add movie rating")
    @PostMapping(path = ["/{movieId}/rating"])
    @ApiResponse(
        responseCode = "400",
        description = "Incorrect input format",
        content = [Content(schema = Schema(implementation = ProblemDetail::class))],
    )
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ApiResponse(responseCode = "200", description = "Successfully added movie rating")
    @ResponseStatus(HttpStatus.CREATED)
    // todo add role/privilege checker
    fun addMovieRating(
        @PathVariable movieId: String,
        @RequestBody body: AddMovieRatingRequest,
    ): ResponseEntity<MovieResponse>
}
