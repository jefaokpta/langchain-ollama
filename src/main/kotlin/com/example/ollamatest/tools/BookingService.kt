package com.example.ollamatest.tools

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

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

    private val log = LoggerFactory.getLogger(BookingService::class.java)

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
        log.info("Checking booking $bookingNumber for $customerName $customerSurname")
        if (!(bookingNumber == this.bookingNumber && customerName.lowercase() == firstName.lowercase() && customerSurname.lowercase() == surname.lowercase())
        ) {
            throw BookingNotFoundException(bookingNumber)
        }
    }

}

internal class BookingNotFoundException(bookingNumber: String) : RuntimeException("Booking $bookingNumber not found")

internal class BookingCannotBeCancelledException(bookingNumber: String) :
    RuntimeException("Booking $bookingNumber cannot be canceled")