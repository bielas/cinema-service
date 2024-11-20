package com.fourthwall.cinemaservice.shared.exception

import com.cloudogu.cb.Command
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validator
import org.springframework.stereotype.Component

@Component
class CommandValidator(private val validator: Validator) {

    fun <T : Command<*>> validateCommand(command: T) {
        val violations: Set<ConstraintViolation<T>> = validator.validate(command)
        if (violations.isNotEmpty()) {
            throw InvalidCommandException(
                "Validation failed for ${command::class.simpleName}", violations
            )
        }
    }
}
