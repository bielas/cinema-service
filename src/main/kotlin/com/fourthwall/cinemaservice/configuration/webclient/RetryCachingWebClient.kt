package com.fourthwall.cinemaservice.configuration.webclient

import org.springframework.http.HttpMethod
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

class RetryCachingWebClient(private val delegate: WebClient) : WebClient by delegate {

    private val cache: ConcurrentHashMap<String, Mono<Any>> = ConcurrentHashMap()

    override fun method(method: HttpMethod): WebClient.RequestBodyUriSpec {
        val requestSpec = delegate.method(method)
        return RetryCachingRequestSpec(requestSpec, cache)
    }

    private class RetryCachingRequestSpec(
        private val delegate: WebClient.RequestBodyUriSpec,
        private val cache: ConcurrentHashMap<String, Mono<Any>>
    ) : WebClient.RequestBodyUriSpec by delegate {

        override fun retrieve(): ResponseSpec {
            return delegate.retrieve()
                .onStatus({ it.is5xxServerError }) { response ->
                    Mono.error(RuntimeException("5xx Error: ${response.statusCode()}"))
                }
                .let { spec ->
                    RetryCachingResponseSpec(spec, cache)
                }
        }
    }

    private class RetryCachingResponseSpec(
        private val delegate: ResponseSpec,
        private val cache: ConcurrentHashMap<String, Mono<Any>>
    ) : ResponseSpec by delegate {

        override fun <T : Any?> bodyToMono(clazz: Class<T>): Mono<T> {
            val cacheKey = clazz.name
            val cachedResult = cache[cacheKey]
            if (cachedResult != null) {
                @Suppress("UNCHECKED_CAST")
                return cachedResult as Mono<T>
            }

            return delegate.bodyToMono(clazz)
                .retryWhen(
                    Retry.fixedDelay(3, Duration.ofSeconds(1))
                        .filter { it is RuntimeException }
                )
                .doOnSuccess { result -> cache[cacheKey] = Mono.justOrEmpty(result) }
        }
    }
}
