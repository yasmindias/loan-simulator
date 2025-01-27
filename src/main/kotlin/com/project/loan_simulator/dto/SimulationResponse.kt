package com.project.loan_simulator.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "Model for responding to a loan simulation request")
class SimulationResponse(
    @field:Schema(
        description = "Total amount to be payed",
        example = "10000",
        type = "BigDecimal",
    )
    val totalAmount: BigDecimal,
    @field:Schema(
        description = "The amount to be payed monthly",
        example = "1500",
        type = "BigDecimal",
    )
    val monthlyPayment: BigDecimal,
    @field:Schema(
        description = "The amount of interest to be paid",
        example = "2",
        type = "BigDecimal",
    )
    val totalInterestPaid: BigDecimal
)