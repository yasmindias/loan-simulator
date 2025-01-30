package com.project.loan_simulator.exception.handler

import com.project.loan_simulator.controller.SimulationController
import com.project.loan_simulator.exception.dto.ErrorMessageEventApi
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(assignableTypes = [SimulationController::class])
class SimulationExceptionHandler {
  @ExceptionHandler(Exception::class)
  fun handleException(
      request: HttpServletRequest,
      response: HttpServletResponse,
      exception: Exception
  ): ResponseEntity<ErrorMessageEventApi> {
    val errorMessage: ErrorMessageEventApi
    if (exception.message!!.contains("simulate.request[")) {
      errorMessage = parseFieldValidationError(exception)
      return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    } else {
      errorMessage = createErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.message)
      return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  private fun parseFieldValidationError(exception: Exception): ErrorMessageEventApi {
    val errors = exception.message!!.split(", ")
    return createErrorMessage(HttpStatus.BAD_REQUEST, "Error on validation", errors)
  }

  private fun createErrorMessage(
      status: HttpStatus,
      message: String?,
      errors: List<String>? = emptyList()
  ): ErrorMessageEventApi {
    val errorMessage =
        ErrorMessageEventApi(
            status = status.value(),
            error = status.reasonPhrase,
            message = message,
            errors = errors ?: mutableListOf())
    return errorMessage
  }
}
