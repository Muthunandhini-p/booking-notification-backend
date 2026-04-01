package com.busbooking.app.controller;

import com.busbooking.app.dto.ApiResponse;
import com.busbooking.app.service.BrevoEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notification/email")
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private BrevoEmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>> sendEmail(@RequestBody Map<String, String> request) {

        String toEmail = request.get("toEmail");
        String subject = request.get("subject");
        String content = request.get("content");

        emailService.sendEmail(toEmail, subject, content);

        return ResponseEntity.ok(ApiResponse.success("Email sent successfully", null));
    }
}