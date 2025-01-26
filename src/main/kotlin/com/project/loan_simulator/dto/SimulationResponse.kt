package com.project.loan_simulator.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Model for responding to a loan simulation request")
class SimulationResponse(

    val totalAmount: Int,
    val monthlyPayment: Float,
    val totalInterest: Float
)