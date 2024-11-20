package com.fourthwall.cinemaservice.configuration

import com.cloudogu.cb.CommandBus
import com.cloudogu.cb.spring.Registry
import com.cloudogu.cb.spring.SpringCommandBus
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommandBusConfiguration {
    @Bean
    fun commandBus(applicationContext: ApplicationContext): CommandBus = springBus(applicationContext)

    private fun springBus(applicationContext: ApplicationContext) =
        SpringCommandBus(Registry(applicationContext))
}
