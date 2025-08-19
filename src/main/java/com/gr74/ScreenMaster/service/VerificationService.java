package com.gr74.ScreenMaster.service;


import com.gr74.ScreenMaster.enums.CodeType;
import com.gr74.ScreenMaster.model.VerificationCode;
import com.gr74.ScreenMaster.repository.VerificationCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationCodeRepository verificationRepository;

    private final EmailService emailService;

    private final VerificationCodeGeneratorService verificationCodeGeneratorService; 

    private final UserDetailsServiceImpl userDetailsService; 

    @Value("${application.mailing.frontend.activation-url}")
    private String url;

    public String generateAndSendVerificationCode(String email) {
        try {
            // Invalidate any existing codes for this email
            verificationRepository.markAllAsUsedByEmail(email);

            // Generate new 6-digit code
            String code = verificationCodeGeneratorService.generateVerificationCode();


            LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
            LocalDateTime futureTime = now.plusMinutes(15);

            VerificationCode verificationCode =  VerificationCode.builder()
                    .email(email)
                    .code(code)
                    .expiresAt(futureTime)
                    .type(CodeType.EMAIL_VERIFICATION)

                    .build();
            verificationRepository.save(verificationCode);

            // Send email
            String verificationUrl = buildVerificationUrl(email, code);
            emailService.sendVerificationEmail(email, code, verificationUrl);

            return code; // Only return for testing, remove in production

        } catch (Exception e) {
            throw new RuntimeException("Failed to send verification code", e);
        }
    }

    public boolean verifyCode(String email, String code) {
        // Clean up expired codes first
        cleanupExpiredCodes();

        Optional<VerificationCode> verificationOpt =
                verificationRepository.findByEmailAndCodeAndUsedFalse(email, code);

        if (verificationOpt.isEmpty()) {
            return false;
        }

        VerificationCode verification = verificationOpt.get();

        if (verification.isExpired()) {
            return false;
        }

        // Mark as used
        verification.setUsed(true);
                
        verificationRepository.save(verification);

        return true;
    }

    public boolean isValidCode(String email, String code) {
        cleanupExpiredCodes();

        Optional<VerificationCode> verificationOpt =
                verificationRepository.findByEmailAndCodeAndUsedFalse(email, code);

        return verificationOpt.isPresent() && verificationOpt.get().isValid();
    }

    public void resendVerificationCode(String email) {
        // Check if the user exists
        try {
            UserDetails user = userDetailsService.loadUserByUsername(email);

            if (user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }

            // Check if there's a recent valid code (prevent spam)
            List<VerificationCode> recentCodes = verificationRepository
                    .findByEmailAndUsedFalseOrderByCreatedAtDesc(email);

            if (!recentCodes.isEmpty()) {
                VerificationCode latestCode = recentCodes.get(0);
                if (latestCode.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(1))) {
                    throw new RuntimeException("Please wait before requesting a new code");
                }
            }

            generateAndSendVerificationCode(email);
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException("No account found with this email address");
        }
    }

    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void cleanupExpiredCodes() {
        verificationRepository.deleteExpiredCodes(LocalDateTime.now());
    }
    
    private String buildVerificationUrl(String email, String code) {
        return String.format(url+"/auth/verify?email=%s&code=%s",
                URLEncoder.encode(email, StandardCharsets.UTF_8), code);
    }
}






