package com.project.loan_simulator.util

import com.project.loan_simulator.dto.SimulationRequest
import java.math.BigDecimal
import java.time.LocalDate

class MockEntityBuild {
    companion object {
        fun simulationRequest(
            totalValue: BigDecimal? = null,
            birthDate: LocalDate? = null,
            paymentTerm: Int? = null
        ) = SimulationRequest(
            totalValue = totalValue ?: BigDecimal(10000),
            birthDate = birthDate ?: LocalDate.now().minusYears(18),
            paymentTerm = paymentTerm ?: 36
        )
    }
}