package com.project.loan_simulator.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.project.loan_simulator.dto.SimulationRequest
import com.project.loan_simulator.exception.handler.GlobalExceptionHandler
import com.project.loan_simulator.service.SimulationService
import com.project.loan_simulator.util.MockEntityBuild
import com.project.loan_simulator.util.MockitoHelper
import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.math.BigDecimal
import java.time.LocalDate

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
    fun `should simulate loan with success`() {
        //given
        val request = MockEntityBuild.simulationRequest()
        val response = MockEntityBuild.simulationResponse()

        //when
        `when`(simulationService.simulateLoan(MockitoHelper.anyObject())).thenReturn(response)

        val result = this.mock.perform(
            MockMvcRequestBuilders.post("/simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk)
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalAmount").value(response.totalAmount))
        result.andExpect(MockMvcResultMatchers.jsonPath("$.monthlyPayment").value(response.monthlyPayment))
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalInterestPaid").value(response.totalInterestPaid))
    }

    @Test
    fun `should return error for underage client`() {
        //given
        val request = SimulationRequest(BigDecimal(10000), LocalDate.now().minusYears(15), 36)

        //when
        val result = this.mock.perform(
            MockMvcRequestBuilders.post("/simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )

        //then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error on validation"))
        result.andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]", containsString("birthDate:")))
        result.andExpect(MockMvcResultMatchers.jsonPath("$.errors.length()").value("1"))
    }

    @Test
    fun `should return error for total amount smaller than 100`() {
        //given
        val request = SimulationRequest(BigDecimal(99), LocalDate.now().minusYears(18), 36)

        //when
        val result = this.mock.perform(
            MockMvcRequestBuilders.post("/simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )

        //then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error on validation"))
        result.andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]", containsString("totalValue:")))
        result.andExpect(MockMvcResultMatchers.jsonPath("$.errors.length()").value("1"))
    }

    @Test
    fun `should return error for payment term smaller than 2`() {
        //given
        val request = SimulationRequest(BigDecimal(10000), LocalDate.now().minusYears(18), 1)

        //when
        val result = this.mock.perform(
            MockMvcRequestBuilders.post("/simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )

        //then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error on validation"))
        result.andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]", containsString("paymentTerm:")))
        result.andExpect(MockMvcResultMatchers.jsonPath("$.errors.length()").value("1"))
    }
}