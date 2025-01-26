package com.project.loan_simulator.validation.validator

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import kotlin.test.assertContains
import kotlin.test.assertTrue

internal class MinimumAgeValidatorTest {
    private val ERROR_MESSAGE = "O cliente deve ser maior de idade."

    private val validator = MinimumAgeValidator()

    @Test
    fun `should find no validation error, if age is equal or bigger than the minimum age`(){
        assertTrue(validator.isValid(LocalDate.parse("2006-01-25"), context = null))
    }

    @Test
    fun `should return validation error, if age is less than minimum age`(){
        val exception = assertThrows(ResponseStatusException::class.java) {
            validator.isValid(LocalDate.parse("2009-01-25"), context = null)
        }
        assertContains(exception.message, ERROR_MESSAGE)
    }
}