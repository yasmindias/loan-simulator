package com.project.loan_simulator.dto

import com.project.loan_simulator.validation.MinimumAge
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Positive
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@Schema(description = "Model for requesting a loan simulation")
data class SimulationRequest(
    @field:Schema(
        description = "Total value requested to loan",
        example = "10000",
        type = "int",
    )
    @field:NotNull
    @field:Positive
    val totalValue: Int,
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
        type = "int",
    )
    @field:NotNull
    @field:Positive
    val paymentTerm: Int
)