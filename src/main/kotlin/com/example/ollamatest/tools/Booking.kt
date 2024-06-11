package com.example.ollamatest.tools

import java.time.LocalDate

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 11/06/2024
 */
class Booking(
    val bookingNumber: String,
    val bookingFrom: LocalDate,
    val bookingTo: LocalDate,
    val customer: Customer
) {
}