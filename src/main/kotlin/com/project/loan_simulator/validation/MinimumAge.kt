package com.project.loan_simulator.validation

import com.project.loan_simulator.validation.validator.MinimumAgeValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [MinimumAgeValidator::class])
@MustBeDocumented
annotation class MinimumAge(
    val message: String = "o cliente deve ser maior de idade",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)
