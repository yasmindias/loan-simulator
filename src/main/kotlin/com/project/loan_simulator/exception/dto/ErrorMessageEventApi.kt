package com.project.loan_simulator.exception.dto

import java.time.OffsetDateTime

data class ErrorMessageEventApi(
    val status: Int? = null,
    val error: String? = null,
    val message: String? = null,
    val errors: List<String> = emptyList(),
    val timestamp: OffsetDateTime = OffsetDateTime.now()
)