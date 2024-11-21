package com.fourthwall.cinemaservice.adapter.output.imdb

import com.fourthwall.cinemaservice.domain.movie.MovieDetails
import com.fourthwall.cinemaservice.domain.movie.MovieDetailsRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class ImdbMovieRepository(
    @Qualifier("imdbWebClient") private val imdbWebClient: WebClient,
    private val imdbProperties: ImdbProperties
) : MovieDetailsRepository {

    override suspend fun get(id: String): MovieDetails {
        val response = imdbWebClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .queryParam("apikey", imdbProperties.apiKey)
                    .queryParam("i", id)
                    .build()
            }
            .retrieve()
            .awaitBody<ImdbMovieResponse>()

        return response.toDomain()
    }
}
