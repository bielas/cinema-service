package com.fourthwall.cinemaservice.adapter.input.api.movie

import com.fourthwall.cinemaservice.adapter.input.api.movie.request.UpdateMovieScheduleRequest
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
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Validated
@Tag(name = "Trainer operations")
@RequestMapping("/v1/self/movies")
// todo add role/privilege checker
interface SelfMovieApi {
    @Operation(summary = "Update movie schedule")
    @PutMapping(path = ["/{movieId}/schedule"])
    @ApiResponse(responseCode = "200", description = "Successfully updated movie schedule")
    @ApiResponse(
        responseCode = "400",
        description = "Incorrect input format",
        content = [Content(schema = Schema(implementation = ProblemDetail::class))],
    )
    @ApiResponse(responseCode = "404", description = "Movie not found")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateMovieSchedule(
        @PathVariable movieId: String,
        @RequestBody body: UpdateMovieScheduleRequest,
    ): ResponseEntity<List<ShowtimeResponse>>
}
