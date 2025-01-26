package com.project.loan_simulator.validation.validator

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertTrue

internal class MinimumAgeValidatorTest {
    private val validator = MinimumAgeValidator()

    @Test
    fun `should find no validation error, if age is equal or bigger than the minimum age`(){
        assertTrue(validator.isValid(LocalDate.now().minusYears(19), context = null))
    }

    @Test
    fun `should return validation error, if age is less than minimum age`(){
        assertFalse(validator.isValid(LocalDate.now().minusYears(15), context = null))
    }
}