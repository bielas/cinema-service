package com.fourthwall.cinemaservice.domain.movie

interface MovieDetailsRepository {
    suspend fun get(id: String): MovieDetails
}
