package com.project.loan_simulator.service

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class SimulationService {

    fun calculateMonthlyPayments(totalAmount: BigDecimal, annualInterestRate: BigDecimal, paymentTerm: Int) : BigDecimal {
        val monthlyInterestRate = getMonthlyInterestRate(annualInterestRate)
        val dividend = (totalAmount * monthlyInterestRate)
        val divisor = BigDecimal(1 - (1 + monthlyInterestRate.toDouble()).pow(paymentTerm*-1))

        return dividend.divide(divisor, 2, RoundingMode.UP)
    }

    fun returnAnnualInterestRateByAge(age: Int): BigDecimal {
        return if (age < 25) BigDecimal(5)
        else if (age in 26..40) BigDecimal(3)
        else if (age in 41..60) BigDecimal(2)
        else BigDecimal(4)
    }

    private fun getMonthlyInterestRate(annualInterestRate: BigDecimal) =
        annualInterestRate.divide(BigDecimal(12), 2, RoundingMode.UP)
}