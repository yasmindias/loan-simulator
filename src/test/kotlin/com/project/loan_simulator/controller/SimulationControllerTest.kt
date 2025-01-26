package com.project.loan_simulator.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.project.loan_simulator.configuration.JacksonConfig
import com.project.loan_simulator.dto.SimulationRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.annotation.Import
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate


@ExtendWith(MockitoExtension::class)
@Import(JacksonConfig::class)
class SimulationControllerTest {

    private lateinit var simulationController: SimulationController
    private lateinit var objectMapper: ObjectMapper
    private lateinit var mock: MockMvc

    @BeforeEach
    fun setUp() {
        this.simulationController = SimulationController()
        this.objectMapper = ObjectMapper().registerModule(JavaTimeModule())
        this.mock = MockMvcBuilders.standaloneSetup(simulationController)
            .setCustomArgumentResolvers(PageableHandlerMethodArgumentResolver())
            .build()
    }

    @Test
    fun `should simulate loan with success`() {
        //given
        val request = SimulationRequest(10000, LocalDate.parse("2005-03-09"), 32)

        //when
        val result = this.mock.perform(
            MockMvcRequestBuilders.post("/simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )

        //then
        result.andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    fun `should return error for underage client`() {
        //given
        val request = SimulationRequest(10000, LocalDate.parse("2007-03-09"), 32)

        //when
        val result = this.mock.perform(
            MockMvcRequestBuilders.post("/simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )

        //then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}