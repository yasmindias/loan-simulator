package com.project.loan_simulator.controller

import com.project.loan_simulator.dto.SimulationRequest
import com.project.loan_simulator.dto.SimulationResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/simulation")
class SimulationController {

    @PostMapping
    fun simulate(@RequestBody @Valid request: SimulationRequest): ResponseEntity<SimulationResponse> {
        return ResponseEntity.noContent().build()
    }
}