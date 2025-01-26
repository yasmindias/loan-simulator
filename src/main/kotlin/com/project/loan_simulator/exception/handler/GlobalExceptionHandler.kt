package com.project.loan_simulator.exception.handler

import com.project.loan_simulator.exception.dto.ErrorMessageEventApi
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: MethodArgumentNotValidException
    ): ResponseEntity<ErrorMessageEventApi> {
        val errors = mutableListOf<String>()
        exception.bindingResult.fieldErrors.forEach { error ->
            errors.add("${error.field}: ${error.defaultMessage}")
        }

        val errorMessage = createErrorMessage(HttpStatus.BAD_REQUEST, "Error on validation", errors)
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: Exception
    ): ResponseEntity<ErrorMessageEventApi> {
        val errorMessage = createErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.message)
        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun createErrorMessage(
        status: HttpStatus, message: String?, errors: List<String>? = emptyList()
    ): ErrorMessageEventApi {
        val errorMessage = ErrorMessageEventApi(
            status = status.value(),
            error = status.reasonPhrase,
            message = message,
            errors = errors ?: mutableListOf()
        )
        return errorMessage
    }
}