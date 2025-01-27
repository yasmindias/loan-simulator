package com.project.loan_simulator.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.project.loan_simulator.exception.handler.GlobalExceptionHandler
import com.project.loan_simulator.service.SimulationService
import com.project.loan_simulator.util.MockEntityBuild
import com.project.loan_simulator.util.MockitoHelper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class SimulationControllerTest{

    private lateinit var simulationController: SimulationController
    private lateinit var simulationService: SimulationService
    private lateinit var objectMapper: ObjectMapper
    private lateinit var mock: MockMvc

    @BeforeEach
    fun setUp() {
        this.simulationService = mock(SimulationService::class.java)
        this.simulationController = SimulationController(simulationService)
        this.objectMapper = ObjectMapper().registerModule(JavaTimeModule())
        this.mock = MockMvcBuilders.standaloneSetup(simulationController)
            .setControllerAdvice(GlobalExceptionHandler())
            .build()
    }

    @Test
    fun `should simulate loan with success for age equal or less than 25`() {
        //given
        val request = MockEntityBuild.simulationRequest()
        val response = MockEntityBuild.simulationResponse()

        //when
        `when`(simulationService.simulateLoan(MockitoHelper.anyObject())).thenReturn(response)

        val result = simulationController.simulate(request)

        //then
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(response.totalAmount, result.body?.totalAmount)
        assertEquals(response.monthlyPayment, result.body?.monthlyPayment)
        assertEquals(response.totalInterestPaid, result.body?.totalInterestPaid)
    }

    @Test
    fun `should simulate loan with success for age between 26 and 40`() {
        //given
        val request = MockEntityBuild.simulationRequest(birthDate = LocalDate.now().minusYears(31))
        val response = MockEntityBuild.simulationResponse(
            BigDecimal(90029.52).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(2500.82).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(80029.52).setScale(2, RoundingMode.HALF_EVEN),
        )

        //when
        `when`(simulationService.simulateLoan(MockitoHelper.anyObject())).thenReturn(response)

        val result = simulationController.simulate(request)

        //then
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(response.totalAmount, result.body?.totalAmount)
        assertEquals(response.monthlyPayment, result.body?.monthlyPayment)
        assertEquals(response.totalInterestPaid, result.body?.totalInterestPaid)
    }

    @Test
    fun `should simulate loan with success for age between 41 and 60`() {
        //given
        val request = MockEntityBuild.simulationRequest(birthDate = LocalDate.now().minusYears(55))
        val response = MockEntityBuild.simulationResponse(
            BigDecimal(61415.64).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(1705.99).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(51415.64).setScale(2, RoundingMode.HALF_EVEN),
        )

        //when
        `when`(simulationService.simulateLoan(MockitoHelper.anyObject())).thenReturn(response)

        val result = simulationController.simulate(request)

        //then
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(response.totalAmount, result.body?.totalAmount)
        assertEquals(response.monthlyPayment, result.body?.monthlyPayment)
        assertEquals(response.totalInterestPaid, result.body?.totalInterestPaid)
    }

    @Test
    fun `should simulate loan with success for age over 60`() {
        //given
        val request = MockEntityBuild.simulationRequest(birthDate = LocalDate.now().minusYears(75))
        val response = MockEntityBuild.simulationResponse(
            BigDecimal(122403.60).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(3400.10).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(112403.60).setScale(2, RoundingMode.HALF_EVEN),
        )

        //when
        `when`(simulationService.simulateLoan(MockitoHelper.anyObject())).thenReturn(response)

        val result = simulationController.simulate(request)

        //then
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(response.totalAmount, result.body?.totalAmount)
        assertEquals(response.monthlyPayment, result.body?.monthlyPayment)
        assertEquals(response.totalInterestPaid, result.body?.totalInterestPaid)
    }
}