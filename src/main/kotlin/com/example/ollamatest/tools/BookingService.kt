package com.example.ollamatest.tools

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 11/06/2024
 */

@Service
class BookingService {

    private val firstName = "John"
    private val surname = "Doe"
    private val bookingNumber = "123456"
    private val daysToStart = 1
    private val daysToEnd = 3

    fun getBookingDetails(bookingNumber: String, customerName: String, customerSurname: String): Booking {
        ensureExists(bookingNumber, customerName, customerSurname)
        val bookingFrom = LocalDate.now().plusDays(daysToStart.toLong())
        val bookingTo = LocalDate.now().plusDays(daysToEnd.toLong())
        // Retrieval from DB mocking
        val customer = Customer(customerName, customerSurname)
        return Booking(bookingNumber, bookingFrom, bookingTo, customer)
    }

    fun cancelBooking(bookingNumber: String, customerName: String, customerSurname: String) {
        ensureExists(bookingNumber, customerName, customerSurname)

        // TODO add logic to double check booking conditions in case the LLM got it wrong.
        // throw new BookingCannotBeCancelledException(bookingNumber);
    }

    private fun ensureExists(bookingNumber: String, customerName: String, customerSurname: String) {
        // Check mocking
        if (!(bookingNumber == this.bookingNumber && customerName.lowercase(Locale.getDefault()) == firstName && customerSurname.lowercase(
                Locale.getDefault()
            ) == surname)
        ) {
            throw BookingNotFoundException(bookingNumber)
        }
    }

}

internal class BookingNotFoundException(bookingNumber: String) : RuntimeException("Booking $bookingNumber not found")

internal class BookingCannotBeCancelledException(bookingNumber: String) :
    RuntimeException("Booking $bookingNumber cannot be canceled")