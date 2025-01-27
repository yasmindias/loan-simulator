package com.project.loan_simulator.util

import com.project.loan_simulator.dto.SimulationRequest
import com.project.loan_simulator.dto.SimulationResponse
import java.math.BigDecimal
import java.math.RoundingMode
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

        fun simulationResponse(
            totalAmount: BigDecimal? = null,
            monthlyPayment: BigDecimal? = null,
            totalInterestPaid: BigDecimal? = null
        ) = SimulationResponse(
            totalAmount = totalAmount ?: BigDecimal(151200.72).setScale(2, RoundingMode.HALF_EVEN),
            monthlyPayment = monthlyPayment ?: BigDecimal(4200.02).setScale(2, RoundingMode.HALF_EVEN),
            totalInterestPaid = totalInterestPaid ?: BigDecimal(141200.72).setScale(2, RoundingMode.HALF_EVEN)
        )
    }
}