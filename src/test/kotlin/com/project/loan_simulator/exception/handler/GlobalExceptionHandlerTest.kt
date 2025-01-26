package com.project.loan_simulator.exception.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.project.loan_simulator.controller.SimulationController
import com.project.loan_simulator.util.MockEntityBuild
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class GlobalExceptionHandlerTest {

    private lateinit var simulationController: SimulationController
    private lateinit var objectMapper: ObjectMapper
    private lateinit var mock: MockMvc

    @BeforeEach
    fun setUp() {
        this.simulationController = SimulationController()
        this.objectMapper = ObjectMapper().registerModule(JavaTimeModule())
        this.mock = MockMvcBuilders.standaloneSetup(simulationController)
            .setCustomArgumentResolvers(PageableHandlerMethodArgumentResolver())
            .setControllerAdvice(GlobalExceptionHandler())
            .build()
    }


    @Test
    fun `should handle with exception MethodArgumentNotValidException`() {
        // given
        val request = MockEntityBuild.simulationRequest(birthDate = LocalDate.now().minusYears(15))

        // when
        val result = this.mock.perform(
            MockMvcRequestBuilders.post("/simulation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
        result.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Bad Request"))
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error on validation"))
        result.andExpect(MockMvcResultMatchers.jsonPath("$.errors.length()").value("1"))
    }

    @Test
    fun `should handle with generic exception`() {
        //TODO implement test
    }
}