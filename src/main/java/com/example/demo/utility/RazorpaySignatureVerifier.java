package com.example.demo.utility;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class RazorpaySignatureVerifier {

    public static boolean verifySignature(String payload, String actualSignature, String secret) {
        log.info("Verifying Razorpay signature.");
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);

            byte[] hash = sha256_HMAC.doFinal(payload.getBytes());

            // Convert bytes to HEX
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String generatedSignature = hexString.toString();

            return generatedSignature.equals(actualSignature);
        } catch (Exception e) {
            log.error("Error while verifying Razorpay signature. Payload: {}, Error: {}", payload, e.getMessage(), e);
            return false;
        }
    }
}
