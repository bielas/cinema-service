package com.fourthwall.cinemaservice.configuration.security

import com.fourthwall.cinemaservice.domain.security.IAuthenticationFacade
import org.springframework.stereotype.Component

@Component
class AuthenticationFacade : IAuthenticationFacade {
    override fun getUserEmail(): String {
        return "john.doe@mail.com"
    }
}
