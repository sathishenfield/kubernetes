package com.example.demo.service;

import com.example.demo.dto.OrderDetails;
import com.example.demo.dto.OrderResponse;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${razorpay.key.id}")
    private String razorPayKey;

    @Value("${razorpay.key.secret}")
    private String razorPaySecret;

    public OrderResponse createOrder(OrderDetails orderDetails) {
        log.info("Creating Razorpay order for customerId: {}, amount: {}",
                orderDetails.getCustomerId(), orderDetails.getAmount());
        try {
            RazorpayClient client = new RazorpayClient(razorPayKey, razorPaySecret);

            JSONObject options = new JSONObject();
            options.put("amount", orderDetails.getAmount().multiply(BigDecimal.valueOf(100)).longValue()); // amount in paise
            options.put("currency", "INR");
            options.put("receipt", generateReceiptNumber());
            options.put("payment_capture", 1);

            Order order = client.orders.create(options);
            if (Objects.nonNull(order)) {
                log.info("Razorpay order created successfully: {}", Optional.ofNullable(order.get("id")).get());
                Transaction transaction = new Transaction();
                transaction.setAmount(orderDetails.getAmount());
                transaction.setOrderId(order.get("id"));
                transaction.setCustomerId(orderDetails.getCustomerId());
                transaction.setReceipt(order.get("receipt"));
                transaction.setStatus(order.get("status"));
                transactionRepository.save(transaction);
                log.info("Transaction saved in DB. OrderId: {}, Receipt: {}, Status: {}",
                        transaction.getOrderId(), transaction.getReceipt(), transaction.getStatus());
            }

            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(order.get("id"));
            orderResponse.setAmount(orderDetails.getAmount());
            return orderResponse;
        } catch (RazorpayException e) {
            log.error("Failed to create Razorpay order for customerId: {}, amount: {}. Error: {}",
                    orderDetails.getCustomerId(), orderDetails.getAmount(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void updateOrder(String payload) {
        log.info("Inside webhook to update order details for payload : {}",payload);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(payload);
            JsonNode entity = root.path("payload").path("payment").path("entity");

            String orderId = entity.path("order_id").asText(null);
            if (StringUtils.isEmpty(orderId)) {
                log.warn("Webhook received without order_id. Payload: {}", payload);
                return;
            }

            Transaction transaction = transactionRepository.findByOrderId(orderId);
            if (transaction == null) {
                log.warn("No transaction found for orderId: {}", orderId);
                return;
            }
            String status = entity.path("status").asText(null);
            transaction.setStatus(status);
            transaction.setPaymentDescription(entity.path("description").asText(null));
            transaction.setPaymentId(entity.path("id").asText(null));
            transaction.setPaymentType(entity.path("method").asText(null));
            transaction.setTransactionDate(new Timestamp(entity.path("created_at").asLong() * 1000));

            if ("failed".equalsIgnoreCase(status)) {
                transaction.setPaymentFailureReason(entity.path("error_description").asText(null));
                log.error("Payment failed for orderId: {}, reason: {}", orderId, transaction.getPaymentFailureReason());
            }

            transactionRepository.save(transaction);
            log.info("Transaction updated successfully. OrderId: {}, Status: {}, PaymentId: {}",
                    orderId, status, transaction.getPaymentId());

        } catch (JsonProcessingException e) {
            log.error("Failed to parse webhook payload: {}", payload, e);
            throw new RuntimeException("Failed to parse webhook payload", e);
        } catch (Exception ex) {
            log.error("Unexpected error while updating transaction", ex);
            throw ex;
        }
    }

    private String generateReceiptNumber() {
        String year = String.valueOf(LocalDate.now().getYear());
        long count = transactionRepository.count() + 1;
        return String.format("ORDER-%s-%04d", year, count);
    }
}
