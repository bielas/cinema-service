package com.fourthwall.cinemaservice.adapter.output.imdb

import com.fourthwall.cinemaservice.domain.movie.MovieDetails
import com.fourthwall.cinemaservice.domain.movie.MovieDetailsRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Service
class ImdbMovieRepository(
    private val restTemplate: RestTemplate,
    private val imdbProperties: ImdbProperties
) : MovieDetailsRepository {

    // todo add cache
    override fun get(id: String): MovieDetails {
        val url = "${imdbProperties.url}/?apikey=${imdbProperties.apiKey}&i=$id"

        return runCatching {
            restTemplate.getForObject<ImdbMovieResponse>(url)
        }.getOrElse { exception ->
            throw IllegalArgumentException(
                "Error while fetching movie details for ID $id: ${exception.message}",
                exception
            )
        }.toDomain()
    }
}
