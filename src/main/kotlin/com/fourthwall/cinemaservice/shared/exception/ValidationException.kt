package com.fourthwall.cinemaservice.shared.exception

open class ValidationException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
