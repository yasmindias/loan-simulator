package com.project.loan_simulator.validation.validator

import com.project.loan_simulator.util.yearsSince
import com.project.loan_simulator.validation.MinimumAge
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate

class MinimumAgeValidator : ConstraintValidator<MinimumAge, LocalDate> {

  companion object {
    private const val MINIMUM_AGE = 18
  }

  override fun isValid(value: LocalDate?, context: ConstraintValidatorContext?): Boolean {
    return value!!.yearsSince() >= MINIMUM_AGE
  }
}
