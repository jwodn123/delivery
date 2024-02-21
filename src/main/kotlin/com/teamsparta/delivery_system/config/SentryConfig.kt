package com.teamsparta.delivery_system.config

import io.sentry.Sentry
import io.sentry.SentryClient
import lombok.extern.slf4j.Slf4j
import org.springframework.context.annotation.Configuration


@Slf4j
@Configuration
class SentryConfig {

    init {
        Sentry.init { options ->
            options.isEnableExternalConfiguration = true
        }
    }

}