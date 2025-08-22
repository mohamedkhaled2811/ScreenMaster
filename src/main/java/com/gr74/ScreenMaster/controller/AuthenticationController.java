package com.gr74.ScreenMaster.controller;


import com.gr74.ScreenMaster.dto.request.AuthenticationRequestDto;
import com.gr74.ScreenMaster.dto.response.AuthenticationResponseDto;
import com.gr74.ScreenMaster.dto.request.RegistrationRequestDto;
import com.gr74.ScreenMaster.dto.request.ResendRequestDto;
import com.gr74.ScreenMaster.dto.request.VerificationRequestDto;
import com.gr74.ScreenMaster.service.AuthenticationService;
import com.gr74.ScreenMaster.service.UserDetailsServiceImpl;
import com.gr74.ScreenMaster.service.VerificationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
//@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService service;
    private final VerificationService verificationService;
    private final UserDetailsServiceImpl userDetailsService;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequestDto request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyFromEmail(@RequestParam String email, @RequestParam String code) {

        UserDetails user = userDetailsService.loadUserByUsername(email);
        boolean isValid = verificationService.verifyCode(email, code);

        if (isValid) {
            userDetailsService.enableUser(email);
            // Redirect to success page
            return ResponseEntity.status(302)
                    .header("Location", "https://yourapp.com/verification-success")
                    .build();
        } else {
            return ResponseEntity.status(400)
                    .body("Invalid or expired verification code");
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestBody @Valid VerificationRequestDto request) {
        try {

            UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());

            boolean isValid = verificationService.verifyCode(request.getEmail(), request.getCode());

            Map<String, String> response = new HashMap<>();

            if (isValid) {
                // Update user as verified in your user service
                // userService.markAsVerified(request.getEmail());
                // Enable the user
                userDetailsService.enableUser(request.getEmail());
                response.put("message", "Email verified successfully");
                response.put("status", "verified");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Invalid or expired verification code");
                return ResponseEntity.status(400).body(response);
            }

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Verification failed: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
    @PostMapping("/resend-verification")
    public ResponseEntity<Map<String, String>> resendVerification(@RequestBody @Valid ResendRequestDto request) {
        try {
            verificationService.resendVerificationCode(request.getEmail());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Verification code sent successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(400).body(error);
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody AuthenticationRequestDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }




}