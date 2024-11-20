package com.fourthwall.cinemaservice.configuration

import com.fourthwall.cinemaservice.domain.DomainException
import com.fourthwall.cinemaservice.shared.exception.ValidationException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class ApiExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(
        value =
        [
            ValidationException::class,
            MethodArgumentTypeMismatchException::class,
            ConstraintViolationException::class,
        ],
    )
    fun handleBadRequestException(ex: Exception): ProblemDetail {
        return BadRequestExceptionHandler.handle(ex)
    }

    @ExceptionHandler(value = [DomainException::class])
    fun handleInternalServerException(ex: Exception): ProblemDetail {
        return InternalErrorExceptionHandler.handle(ex)
    }

    @ExceptionHandler(value = [DomainException.DomainNotFoundException::class])
    fun handleNotFoundException(ex: Exception): ProblemDetail {
        return NotFoundErrorExceptionHandler.handle(ex)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val body = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)

        ex.bindingResult.fieldErrors.forEach { body.setProperty(it.field, it.defaultMessage) }

        ex.bindingResult.globalErrors.forEach { body.setProperty(it.objectName, it.defaultMessage) }

        return super.handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val body = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)

        body.detail =
            "Invalid request message sent. JSON deserialization failed or invalid enum value specified"
        body.setProperty("when", LocalDateTime.now())

        return super.handleExceptionInternal(ex, body, headers, status, request)
    }

    class BadRequestExceptionHandler {
        companion object {
            fun handle(ex: Exception): ProblemDetail {
                return ProblemDetail.forStatusAndDetail(
                    HttpStatus.BAD_GATEWAY,
                    ex.message ?: "validation error",
                )
            }
        }
    }

    class InternalErrorExceptionHandler {
        companion object {
            fun handle(ex: Exception): ProblemDetail {
                return ProblemDetail.forStatusAndDetail(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.message ?: "Internal server error",
                )
            }
        }
    }

    class NotFoundErrorExceptionHandler {
        companion object {
            fun handle(ex: Exception): ProblemDetail {
                return ProblemDetail.forStatusAndDetail(
                    HttpStatus.NOT_FOUND,
                    ex.message ?: "Not found",
                )
            }
        }
    }
}
