package com.fourthwall.cinemaservice.domain.movie

interface MovieDetailsRepository {
    fun get(id: String): MovieDetails
}
