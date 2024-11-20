package com.fourthwall.cinemaservice.domain

open class DomainException(
    override val message: String,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause) {
    class DomainNotFoundException(domainName: String, domainId: String) :
        DomainException("$domainName with id $domainId not found")

    class DomainInternalException(domainName: String, cause: Throwable? = null) :
        DomainException("$domainName has internal exception", cause)
}
