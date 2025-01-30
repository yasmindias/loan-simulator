package com.project.loan_simulator.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.project.loan_simulator.exception.handler.SimulationExceptionHandler
import com.project.loan_simulator.service.SimulationService
import com.project.loan_simulator.util.MockEntityBuild
import java.math.BigDecimal
import java.time.LocalDate
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(MockitoExtension::class)
class SimulationControllerTest {

  private lateinit var simulationController: SimulationController
  private lateinit var simulationService: SimulationService
  private lateinit var objectMapper: ObjectMapper
  private lateinit var client: WebTestClient

  @BeforeEach
  fun setUp() {
    this.simulationService = SimulationService()
    this.simulationController = SimulationController(simulationService)
    this.objectMapper = ObjectMapper().registerModule(JavaTimeModule())
    this.client =
        WebTestClient.bindToController(simulationController)
            .controllerAdvice(SimulationExceptionHandler())
            .build()
  }

  @Test
  fun `should simulate loan with success`() {
    // given
    val request = listOf(MockEntityBuild.simulationRequest())
    val response = MockEntityBuild.simulationResponse()

    // when/then
    runBlocking {
      client
          .post()
          .uri("/simulation")
          .bodyValue(request)
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody()
          .jsonPath("$[0].totalAmount")
          .isEqualTo(response.totalAmount)
          .jsonPath("$[0].monthlyPayment")
          .isEqualTo(response.monthlyPayment)
          .jsonPath("$[0].totalInterestPaid")
          .isEqualTo(response.totalInterestPaid)
    }
  }

  @Test
  fun `should return error for underage client`() {
    // given
    val request = MockEntityBuild.simulationRequest(birthDate = LocalDate.now().minusYears(15))

    // when/then
    client.post().uri("/simulation").bodyValue(request).exchange().expectStatus().isBadRequest.expectBody()
  }

  @Test
  fun `should return error for total amount smaller than 100`() {
    // given
    val request = MockEntityBuild.simulationRequest(totalValue = BigDecimal(10))

    // when/then
    client.post().uri("/simulation").bodyValue(request).exchange().expectStatus().isBadRequest.expectBody()
  }

  @Test
  fun `should return error for payment term smaller than 2`() {
    // given
    val request = MockEntityBuild.simulationRequest(paymentTerm = 1)

    // when/then
    client.post().uri("/simulation").bodyValue(request).exchange().expectStatus().isBadRequest.expectBody()
  }
}
