package com.fourthwall.cinemaservice.adapter.output.imdb

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "imdb.api")
data class ImdbProperties(
    val apiKey: String,
    val url: String,
)
