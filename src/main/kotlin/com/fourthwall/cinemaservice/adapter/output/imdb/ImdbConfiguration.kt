package com.fourthwall.cinemaservice.adapter.output.imdb

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    ImdbProperties::class,
)
class ImdbConfiguration
