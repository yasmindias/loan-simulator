package com.project.loan_simulator.dto

import com.project.loan_simulator.validation.MinimumAge
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

data class SimulationRequest(
    @field:NotNull
    val totalValue: Int,
    @field:NotNull
    @MinimumAge
    val birthDate: LocalDate,
    @field:NotNull
    val paymentTerm: Int
)