package com.fourthwall.cinemaservice.adapter.output.imdb

import com.fourthwall.cinemaservice.domain.movie.MovieDetails
import com.fourthwall.cinemaservice.domain.movie.MovieDetailsRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class ImdbMovieRepository(
    private val webClientBuilder: WebClient.Builder,
    private val imdbProperties: ImdbProperties
) : MovieDetailsRepository {

    private val webClient: WebClient by lazy {
        webClientBuilder.baseUrl(imdbProperties.url).build()
    }

    // todo add cache
    override fun get(id: String): MovieDetails {
        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .queryParam("apikey", imdbProperties.apiKey)
                    .queryParam("i", id)
                    .build()
            }
            .retrieve()
            .bodyToMono(ImdbMovieResponse::class.java)
            .block()

        return response?.toDomain() ?: throw IllegalArgumentException("Movie not found")
    }
}
