package com.project.loan_simulator.controller

import com.project.loan_simulator.dto.SimulationRequest
import com.project.loan_simulator.dto.SimulationResponse
import com.project.loan_simulator.service.SimulationService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Loan Simulation", description = "API to simulate loans")
@Validated
@RestController
@RequestMapping("/simulation")
class SimulationController(private val simulationService: SimulationService) {

    @PostMapping
    fun simulate(@RequestBody @Valid request: SimulationRequest): ResponseEntity<SimulationResponse> {
        val response = simulationService.simulateLoan(request)
        return ResponseEntity.ok(response)
    }
}