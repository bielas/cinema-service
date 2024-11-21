package com.fourthwall.cinemaservice.adapter.output.imdb

import com.fourthwall.cinemaservice.configuration.webclient.RetryCachingWebClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ImdbWebClientConfiguration {

    @Bean
    fun imdbWebClient(imdbProperties: ImdbProperties): WebClient {
        val baseClient = WebClient.builder()
            .baseUrl(imdbProperties.url)
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("apikey", imdbProperties.apiKey)
            .build()

        return RetryCachingWebClient(baseClient)
    }
}
