package com.fourthwall.cinemaservice.shared.exception

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import java.util.Collections

class InvalidCommandException : ConstraintViolationException {
    constructor(
        message: String,
        constraintViolations: Set<ConstraintViolation<*>>,
    ) : super(
        "$message. Violations: " +
                constraintViolations.joinToString { violation ->
                    val className = violation.rootBeanClass.simpleName
                    val property = violation.propertyPath.toString()
                    val violationMessage = violation.message
                    "$className.$property.$violationMessage"
                },
        constraintViolations,
    )

    constructor(message: String) : super(message, Collections.emptySet<ConstraintViolation<*>>())
}
