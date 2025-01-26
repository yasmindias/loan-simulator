package com.project.loan_simulator.validation.validator

import com.project.loan_simulator.validation.MinimumAge
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.time.Period

class MinimumAgeValidator : ConstraintValidator<MinimumAge, LocalDate> {

    companion object {
        private const val MINIMUM_AGE = 18
        private const val ERROR_MESSAGE = "O cliente deve ser maior de idade."
    }

    override fun isValid(value: LocalDate?, context: ConstraintValidatorContext?): Boolean {
        val period = Period.between(value, LocalDate.now())
        if(period.years < MINIMUM_AGE) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ERROR_MESSAGE)
        } else {
            return true
        }
    }
}