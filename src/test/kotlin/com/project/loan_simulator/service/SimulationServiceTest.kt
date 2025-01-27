package com.project.loan_simulator.service

import com.project.loan_simulator.util.MockEntityBuild
import com.project.loan_simulator.util.yearsSince
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import kotlin.test.assertEquals

class SimulationServiceTest {
    private lateinit var simulationService: SimulationService

    @BeforeEach
    fun setUp() {
        simulationService = SimulationService()
    }

    @Test
    fun `should return simulation data with success`() {
        //given
        val request = MockEntityBuild.simulationRequest()

        //when
        val result = simulationService.simulateLoan(request)

        //then
        val expectedTotalAmount = BigDecimal(151200.72).setScale(2, RoundingMode.HALF_EVEN)
        val expectedMonthlyPayment = BigDecimal(4200.02).setScale(2, RoundingMode.HALF_EVEN)
        val expectedInterestPaid = BigDecimal(141200.72).setScale(2, RoundingMode.HALF_EVEN)

        assertEquals(expectedTotalAmount, result.totalAmount)
        assertEquals(expectedMonthlyPayment, result.monthlyPayment)
        assertEquals(expectedInterestPaid, result.totalInterestPaid)
    }

    @Test
    fun `should return 5 percent interest rate if age is equal or less than 25`() {
        //given
        val birthDate = LocalDate.now().minusYears(23)

        //when
        val rate = simulationService.returnAnnualInterestRateByAge(birthDate.yearsSince())

        //then
        assertEquals(BigDecimal(5), rate)
    }

    @Test
    fun `should return the amount for monthly payments with success, for age equal or less than 25`() {
        //given
        val request = MockEntityBuild.simulationRequest()
        val annualInterestRate = simulationService.returnAnnualInterestRateByAge(request.birthDate.yearsSince())

        //when
        val result = simulationService.calculateMonthlyPayments(
            request.totalValue,
            annualInterestRate,
            request.paymentTerm)

        //then
        val expected = BigDecimal(4200.02).setScale(2, RoundingMode.HALF_EVEN)
        assertEquals(expected, result)
    }

    @Test
    fun `should return 3 percent interest rate if age is between 26 and 40`() {
        //given
        val birthDate = LocalDate.now().minusYears(36)

        //when
        val rate = simulationService.returnAnnualInterestRateByAge(birthDate.yearsSince())

        //then
        assertEquals(BigDecimal(3), rate)
    }

    @Test
    fun `should return the amount for monthly payments with success, for age between 26 and 40`() {
        //given
        val request = MockEntityBuild.simulationRequest(birthDate = LocalDate.now().minusYears(31))
        val annualInterestRate = simulationService.returnAnnualInterestRateByAge(request.birthDate.yearsSince())

        //when
        val result = simulationService.calculateMonthlyPayments(
            request.totalValue,
            annualInterestRate,
            request.paymentTerm)

        //then
        val expected = BigDecimal(2500.82).setScale(2, RoundingMode.HALF_EVEN)
        assertEquals(expected, result)
    }

    @Test
    fun `should return 2 percent interest rate if age is between 41 and 60 `() {
        //given
        val birthDate = LocalDate.now().minusYears(51)

        //when
        val rate = simulationService.returnAnnualInterestRateByAge(birthDate.yearsSince())

        //then
        assertEquals(BigDecimal(2), rate)
    }

    @Test
    fun `should return the amount for monthly payments with success, for age between 41 and 60`() {
        //given
        val request = MockEntityBuild.simulationRequest(birthDate = LocalDate.now().minusYears(57))
        val annualInterestRate = simulationService.returnAnnualInterestRateByAge(request.birthDate.yearsSince())

        //when
        val result = simulationService.calculateMonthlyPayments(
            request.totalValue,
            annualInterestRate,
            request.paymentTerm)

        //then
        val expected = BigDecimal(1705.99).setScale(2, RoundingMode.HALF_EVEN)
        assertEquals(expected, result)
    }

    @Test
    fun `should return 4 percent interest rate if age is over 60`() {
        //given
        val birthDate = LocalDate.now().minusYears(74)

        //when
        val rate = simulationService.returnAnnualInterestRateByAge(birthDate.yearsSince())

        //then
        assertEquals(BigDecimal(4), rate)
    }

    @Test
    fun `should return the amount for monthly payments with success, for age bigger than 60`() {
        //given
        val request = MockEntityBuild.simulationRequest(birthDate = LocalDate.now().minusYears(68))
        val annualInterestRate = simulationService.returnAnnualInterestRateByAge(request.birthDate.yearsSince())

        //when
        val result = simulationService.calculateMonthlyPayments(
            request.totalValue,
            annualInterestRate,
            request.paymentTerm)

        //then
        val expected = BigDecimal(3400.10).setScale(2, RoundingMode.HALF_EVEN)
        assertEquals(expected, result)
    }
}