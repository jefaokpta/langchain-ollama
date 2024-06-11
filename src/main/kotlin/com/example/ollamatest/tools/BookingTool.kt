package com.example.ollamatest.tools

import dev.langchain4j.agent.tool.Tool
import org.springframework.stereotype.Service

/**
 * @author Jefferson Alves Reis (jefaokpta) < jefaokpta@hotmail.com >
 * Date: 11/06/2024
 */

@Service
class BookingTool(private val bookingService: BookingService) {

    @Tool
    fun getBookingDetails(bookingNumber: String, customerName: String, customerSurname: String): Booking {
        return bookingService.getBookingDetails(bookingNumber, customerName, customerSurname)
    }

    @Tool
    fun cancelBooking(bookingNumber: String, customerName: String, customerSurname: String) {
        bookingService.cancelBooking(bookingNumber, customerName, customerSurname)
    }
}