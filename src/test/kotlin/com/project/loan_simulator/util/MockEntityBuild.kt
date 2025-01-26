package com.project.loan_simulator.util

import com.project.loan_simulator.dto.SimulationRequest
import java.time.LocalDate

class MockEntityBuild {
    companion object {
        fun simulationRequest(
            totalValue: Int? = null,
            birthDate: LocalDate? = null,
            paymentTerm: Int? = null
        ) = SimulationRequest(
            totalValue = totalValue ?: 10000,
            birthDate = birthDate ?: LocalDate.now().minusYears(18),
            paymentTerm = paymentTerm ?: 32
        )
    }
}