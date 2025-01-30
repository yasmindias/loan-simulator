package com.project.loan_simulator.dto

import com.project.loan_simulator.validation.MinimumAge
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDate
import org.jetbrains.annotations.NotNull

@Schema(description = "Model for requesting a loan simulation")
data class SimulationRequest(
    @field:Schema(
        description = "Total value requested to loan",
        example = "10000",
        type = "BigDecimal",
    )
    @field:NotNull
    @field:Positive
    @field:Min(100)
    val totalValue: BigDecimal,
    @field:Schema(
        description = "Date of birth of the client, must be sent as a string following the format YYYY-MM-DD",
        example = "1989-05-27",
        type = "LocalDate",
    )
    @field:NotNull
    @MinimumAge
    val birthDate: LocalDate,
    @field:Schema(
        description = "The length of the monthly payments to be made",
        example = "36",
        type = "Int",
    )
    @field:NotNull
    @field:Positive
    @field:Min(2)
    val paymentTerm: Int
)
