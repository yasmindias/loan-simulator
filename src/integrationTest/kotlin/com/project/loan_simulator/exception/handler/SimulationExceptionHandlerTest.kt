package com.project.loan_simulator.exception.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.project.loan_simulator.controller.SimulationController
import com.project.loan_simulator.service.SimulationService
import com.project.loan_simulator.util.MockEntityBuild
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class SimulationExceptionHandlerTest {

    private lateinit var simulationController: SimulationController
    private lateinit var simulationService: SimulationService
    private lateinit var objectMapper: ObjectMapper
    private lateinit var client: WebTestClient

    @BeforeEach
    fun setUp() {
        this.simulationService = SimulationService()
        this.simulationController = SimulationController(simulationService)
        this.objectMapper = ObjectMapper().registerModule(JavaTimeModule())
        this.client = WebTestClient.bindToController(simulationController)
            .controllerAdvice(SimulationExceptionHandler())
            .build()
    }


    @Test
    fun `should handle validation exception `() {
        // given
        val request = MockEntityBuild.simulationRequest(birthDate = LocalDate.now().minusYears(15))

        //when/then
        client.post().uri("/simulation")
            .bodyValue(request)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
    }
}