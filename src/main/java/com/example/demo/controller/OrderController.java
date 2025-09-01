package com.example.demo.controller;

import com.example.demo.dto.OrderDetails;
import com.example.demo.dto.OrderResponse;
import com.example.demo.service.OrderService;
import com.example.demo.utility.RazorpaySignatureVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Value("${razorpay.webhook.secret}")
    private String WEBHOOK_SECRET;

    @PostMapping("/create-order")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderDetails orderDetails) {
        log.info("Create Order request received. CustomerId: {}, Amount: {}",
                orderDetails.getCustomerId(), orderDetails.getAmount());
        try {
            OrderResponse orderResponse = orderService.createOrder(orderDetails);
            log.info("Order created successfully. OrderId: {}, Amount: {}",
                    orderResponse.getOrderId(), orderResponse.getAmount());
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            log.error("Failed to create order for CustomerId: {}. Error: {}",
                    orderDetails.getCustomerId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload,
                                                @RequestHeader("X-Razorpay-Signature") String signature) {
        log.info(" Webhook received. Payload size: {} bytes", payload != null ? payload.length() : 0);
        try {
            boolean isValid = RazorpaySignatureVerifier.verifySignature(payload, signature, WEBHOOK_SECRET);
            if (!isValid) {
                log.warn("Invalid Razorpay signature. Signature: {}", signature);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
            } else {
                log.info("Signature verified successfully. Processing order update...");
                orderService.updateOrder(payload);
            }
            log.info("Webhook processed successfully for payload : {}", payload);
            return ResponseEntity.ok("Webhook processed");
        } catch (Exception e) {
            log.error("Error while handling Razorpay webhook. Payload snippet: {}..., Error: {}", payload,
                    e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing webhook");
        }
    }


}
