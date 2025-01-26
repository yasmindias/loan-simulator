package com.project.loan_simulator.validation.validator

import com.project.loan_simulator.validation.MinimumAge
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate
import java.time.Period

class MinimumAgeValidator : ConstraintValidator<MinimumAge, LocalDate> {

    companion object {
        private const val MINIMUM_AGE = 18
    }

    override fun isValid(value: LocalDate?, context: ConstraintValidatorContext?): Boolean {
        val period = Period.between(value, LocalDate.now())
        return period.years < MINIMUM_AGE
    }
}