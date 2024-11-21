package com.fourthwall.cinemaservice.configuration

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        val httpClient = HttpClient.create(ConnectionProvider.create("custom"))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(10, TimeUnit.SECONDS))
                conn.addHandlerLast(WriteTimeoutHandler(10, TimeUnit.SECONDS))
            }

        val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)
            }
            .build()

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .exchangeStrategies(exchangeStrategies)
            .build()
    }
}
