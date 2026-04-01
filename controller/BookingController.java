package com.busbooking.app.controller;

import com.busbooking.app.dto.ApiResponse;
import com.busbooking.app.entity.Booking;
import com.busbooking.app.service.BookingService;
import com.busbooking.app.service.BrevoEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BrevoEmailService emailService;

    @PostMapping
    public ResponseEntity<ApiResponse<Booking>> createBooking(@RequestBody Map<String, Object> request) {

        Long busId = Long.valueOf(request.get("busId").toString());
        List<Long> seatIds = (List<Long>) request.get("seatIds");

        Booking booking = bookingService.createBooking(busId, seatIds);
        try {
            String subject = "Booking Confirmed";

            String content = "<h2>Booking Successful 🎉</h2>"
                    + "<p>Booking ID: " + booking.getBookingId() + "</p>"
                    + "<p>Total Amount: ₹" + booking.getTotalAmount() + "</p>";

            emailService.sendEmail(
                    booking.getUser().getEmail(),
                    subject,
                    content
            );

            System.out.println("✅ Email sent from controller");

        } catch (Exception e) {
            System.out.println("❌ Email failed: " + e.getMessage());
        }

        return ResponseEntity.ok(
                ApiResponse.success("Booking successful", booking)
        );
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<Booking>>>
    getUserBookings() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Bookings retrieved successfully",
                        bookingService.getUserBookings()
                )
        );
    }
}