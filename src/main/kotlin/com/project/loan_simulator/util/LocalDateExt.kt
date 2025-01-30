package com.project.loan_simulator.util

import java.time.LocalDate
import java.time.Period

fun LocalDate.yearsSince(): Int {
  return Period.between(this, LocalDate.now()).years
}
