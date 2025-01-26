package com.project.loan_simulator.util

import com.project.loan_simulator.dto.SimulationRequest
import java.time.LocalDate

class MockEntityBuild {
    companion object {
        fun simulationRequest(
            totalValue: Int? = 1,
            birthDate: LocalDate? = LocalDate.now(),
            paymentTerm: Int? = 1
        ) = SimulationRequest(
            totalValue = totalValue ?: 10000,
            birthDate = birthDate ?: LocalDate.parse("2005-03-09"),
            paymentTerm = paymentTerm ?: 32
        )
    }
}