package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mock")
public class StreamController {

//    @PostMapping("/token")
    public ResponseEntity<String> retrieveToken(
            @RequestHeader("X-API-Key") String apiKey,
            @RequestParam("AccountNumber") String accountNumber,
            @RequestParam("UserCode") String userCode,
            @RequestParam("BureauName") String bureauName,
            @RequestParam("Password") String password,
            @RequestParam("CallingModule") String callingModule
    ) {

        if (!"sdaghd-342k-424nc".equals(apiKey)) {
            return ResponseEntity.status(401).body("{\"error\": \"Invalid API Key\"}");
        }
        // Validate example (you can adjust)
        if ("ABC123".equals(accountNumber) && "myusercode".equals(userCode)) {
            String response = "{\n" +
                    "  \"http_code\": \"200\",\n" +
                    "  \"status\": \"success\",\n" +
                    "  \"meta\": {\n" +
                    "    \"issuedAt\": \"2025-07-04T12:34:56Z\",\n" +
                    "    \"expiresInSeconds\": 3600\n" +
                    "  },\n" +
                    "  \"Results\": [\n" +
                    "    {\n" +
                    "      \"tokenData\": {\n" +
                    "        \"tokenz\": \"mocked-token-999xyz\"\n" +
                    "      },\n" +
                    "      \"type\": \"Bearer\",\n" +
                    "      \"scope\": \"access:partner\",\n" +
                    "      \"valid\": true\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"tokenData\": {\n" +
                    "        \"tokenz\": \"mocked-token-abc456\"\n" +
                    "      },\n" +
                    "      \"type\": \"Bearer\",\n" +
                    "      \"scope\": \"access:partner\",\n" +
                    "      \"valid\": true\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Invalid credentials provided\"}");
        }
    }

    @PostMapping("/token")
    public ResponseEntity<JsonNode> handleTokenRequest(@RequestParam Map<String, String> formParams) throws JsonProcessingException {

        // Access form parameters
        String accountNumber = formParams.get("AccountNumber");
        String userCode = formParams.get("UserCode");
        String bureauName = formParams.get("BureauName");
        String password = formParams.get("Password");
        String callingModule = formParams.get("CallingModule");

        // Optional: Log or use values
//        System.out.println("API Key: " + apiKey);
        System.out.println("AccountNumber: " + accountNumber);
        System.out.println("UserCode: " + userCode);
        System.out.println("BureauName: " + bureauName);
        System.out.println("Password: " + password);
        System.out.println("CallingModule: " + callingModule);
        String response = "{\n" +
                "  \"http_code\": \"200\",\n" +
                "  \"status\": \"success\",\n" +
                "  \"meta\": {\n" +
                "    \"issuedAt\": \"2025-07-04T12:34:56Z\",\n" +
                "    \"expiresInSeconds\": 3600\n" +
                "  },\n" +
                "  \"Results\": [\n" +
                "    {\n" +
                "      \"tokenData\": {\n" +
                "        \"tokenz\": \"mocked-token-999xyz\"\n" +
                "      },\n" +
                "      \"type\": \"Bearer\",\n" +
                "      \"scope\": \"access:partner\",\n" +
                "      \"valid\": true\n" +
                "    },\n" +
                "    {\n" +
                "      \"tokenData\": {\n" +
                "        \"tokenz\": \"mocked-token-abc456\"\n" +
                "      },\n" +
                "      \"type\": \"Bearer\",\n" +
                "      \"scope\": \"access:partner\",\n" +
                "      \"valid\": true\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response);
        return ResponseEntity.ok(jsonResponse);
    }



    @PostMapping("/token/check")
    public ResponseEntity<JsonNode> handleTokenCheckRequest(@RequestParam Map<String, String> formParams) throws JsonProcessingException {

        // Access form parameters
        String accountNumber = formParams.get("PermissiblePurpose");
        String userCode = formParams.get("IDNumber");
        String token = formParams.get("Token");


        // Optional: Log or use values

        System.out.println("AccountNumber: " + accountNumber);
        System.out.println("UserCode: " + userCode);
        System.out.println("Token: " + token);


        String response = "{\n" +
                "  \"http_code\": \"200\",\n" +
                "  \"status\": \"success\",\n" +
                "  \"meta\": {\n" +
                "    \"issuedAt\": \"2025-07-04T12:34:56Z\",\n" +
                "    \"expiresInSeconds\": 3600\n" +
                "  },\n" +
                "  \"Results\": [\n" +
                "    {\n" +
                "      \"tokenData\": {\n" +
                "        \"tokenz\": \"mocked-token-999xyz\"\n" +
                "      },\n" +
                "      \"type\": \"Bearer\",\n" +
                "      \"scope\": \"access:partner\",\n" +
                "      \"valid\": true\n" +
                "    },\n" +
                "    {\n" +
                "      \"tokenData\": {\n" +
                "        \"tokenz\": \"mocked-token-abc456\"\n" +
                "      },\n" +
                "      \"type\": \"Bearer\",\n" +
                "      \"scope\": \"access:partner\",\n" +
                "      \"valid\": true\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response);
        return ResponseEntity.ok(jsonResponse);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(
            @RequestHeader("X-API-Key") String apiKey,
            @RequestParam("token") String token,
            @RequestParam("validateType") String validateType,
            @RequestParam("file1") String file1Content,
            @RequestParam("file2") String file2Content
    ) {
        if (!"sdaghd-342k-424nc".equals(apiKey)) {
            return ResponseEntity.status(401).body("{\"error\": \"Invalid API Key\"}");
        }

        if (!"Aes".equals(validateType)) {
            return ResponseEntity.badRequest().body("{\"error\": \"Invalid validateType value\"}");
        }

        if ("mocked-token-999xyz".equals(token) || "mocked-token-abc456".equals(token)) {
            // You can log or process file1Content and file2Content if needed
            String response = "{\n" +
                    "  \"code\": \"200\",\n" +
                    "  \"message\": \"Token is valid\",\n" +
                    "  \"status\": \"success\",\n" +
                    "  \"details\": [\n" +
                    "    {\n" +
                    "      \"type\": \"validation\",\n" +
                    "      \"status\": \"passed\",\n" +
                    "      \"timestamp\": \"2025-07-04T12:00:00Z\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"type\": \"authorization\",\n" +
                    "      \"status\": \"actives\",\n" +
                    "      \"timestamp\": \"2025-07-04T12:00:01Z\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Invalid token\"}");
        }
    }

    @GetMapping("/get/data")
    public ResponseEntity<String> retrieveToken(
            @RequestHeader("Authorization") String token
    ) {

        if ("mocked-token-999xyz".equals(token)) {
            return ResponseEntity.status(200).body("{\"Success\": \"Valid API\"}");
        }
        return ResponseEntity.status(401).body("{\"error\": \"Invalid API\"}");
    }

    @PostMapping("/post/data/{id}")
    public ResponseEntity<Map<String, Object>> receiveData(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable("id") String id,
            @RequestParam(value = "type", required = false) String type,
            @RequestBody(required = false) Map<String, Object> body
    ) {
        Map<String, Object> errorResponse = new HashMap<>();

        // Check authorization token
        if (token == null || !token.equals("mocked-token-999xyz")) {
            errorResponse.put("error", "Invalid or missing Authorization token");
            return ResponseEntity.status(401).body(errorResponse);
        }

        // Check query parameter
        if (type == null || type.isEmpty()) {
            errorResponse.put("error", "Missing query parameter 'type'");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Check request body
        if (body == null || body.isEmpty()) {
            errorResponse.put("error", "Missing request body");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Success response
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", "Received id: " + id + ", type: " + type + ", body: " + body);
        return ResponseEntity.ok(successResponse);
    }

    @PostMapping("/post")
    public ResponseEntity<Map<String, Object>> test(@RequestBody Map<String,Object> data){
        System.out.println(data);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", "Received");
        return ResponseEntity.ok(successResponse);
    }

    @PostMapping("/post/copy")
    public ResponseEntity<Map<String, Object>> testf(@RequestBody Map<String,Object> data){
        System.out.println(data);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success_copy", "Received");
        return ResponseEntity.ok(successResponse);
    }

    @GetMapping("/text")
    public ResponseEntity<String> handleTextContent() {
        // Your mocked JSON response
        String response = "{\n" +
                "  \"http_code\": \"200\",\n" +
                "  \"status\": \"success\",\n" +
                "  \"meta\": {\n" +
                "    \"issuedAt\": \"2025-07-04T12:34:56Z\",\n" +
                "    \"expiresInSeconds\": 3600\n" +
                "  },\n" +
                "  \"Results\": [\n" +
                "    {\n" +
                "      \"tokenData\": {\n" +
                "        \"tokenz\": \"mocked-token-999xyz\"\n" +
                "      },\n" +
                "      \"type\": \"Bearer\",\n" +
                "      \"scope\": \"access:partner\",\n" +
                "      \"valid\": true\n" +
                "    },\n" +
                "    {\n" +
                "      \"tokenData\": {\n" +
                "        \"tokenz\": \"mocked-token-abc456\"\n" +
                "      },\n" +
                "      \"type\": \"Bearer\",\n" +
                "      \"scope\": \"access:partner\",\n" +
                "      \"valid\": true\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Return as plain text
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(response);
    }

}
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//import reactor.core.publisher.Flux;
//
//import java.io.IOException;
//import java.time.Duration;
//
//@RestController
//public class StreamController {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private final WebClient webClient = WebClient.builder()
//            .baseUrl("https://api.osint.industries")
//            .defaultHeader(HttpHeaders.ACCEPT, "application/json")
//            .defaultHeader("api-key", "cefa312f9b41bd995a3f820be08e25e9") // Replace
//            .build();
//
//    @GetMapping("/event-stream/{needStream}")
//    public SseEmitter streamEvents(@PathVariable boolean needStream) {
//        if (needStream) {
//            return streamWithEmitter("email", "karim@scarybyte.co.za");
//        } else {
//            System.out.println("Message Send from try catch");
//            JsonNode jsonNode = objectMapper.createObjectNode().put("message", "Regular JSON response");
//            return null;
//        }
//    }
//
//    /**
//     * Method 1 – Prepares and returns the emitter while calling the API.
//     */
//    private SseEmitter streamWithEmitter(String type, String query) {
//        SseEmitter emitter = new SseEmitter(0L); // Infinite timeout
//
//        Flux<String> flux = callStreamingApi(type, query, emitter);
//        flux.subscribe(chunk -> {
//            try {
//                emitter.send(SseEmitter.event().data(chunk));
//                System.out.println("Message Send from try catch");
//            } catch (IllegalStateException e) {
//                // Already completed, ignore or log
//            } catch (IOException e) {
//                emitter.completeWithError(e);
//            }
//        });
//
//        return emitter;
//    }
//
//    /**
//     * Method 2 – Makes the API call and returns a Flux of data.
//     */
//    private Flux<String> callStreamingApi(String type, String query, SseEmitter emitter) {
//        try{
//            return webClient.get()
//                    .uri(uriBuilder -> uriBuilder
//                            .path("/v2/request/stream")
//                            .queryParam("type", type)
//                            .queryParam("query", query)
//                            .build())
//                    .accept(MediaType.APPLICATION_NDJSON, MediaType.APPLICATION_JSON, MediaType.TEXT_EVENT_STREAM)
//                    .retrieve()
//                    .bodyToFlux(String.class)
//                    .doOnError(error -> {
//                        try {
//                            emitter.send(SseEmitter.event().name("error").data("Error: " + error.getMessage()));
//                        } catch (IOException e) {
//                            // Ignore inner error
//                        }
//                        emitter.completeWithError(error);
//                    })
//                    .doOnComplete(emitter::complete);
//        }catch (Exception e){
//            System.out.println("============= callStreamingApi  ==========");
//        }
//        System.out.println("============= end ==========");
//      return null;
//    }
//
//    public Flux<String> streamSampleDataa() {
//        return Flux.interval(Duration.ofSeconds(1))
//                .map(i -> "Streaming data " + i)
//                .take(10); // limit to 10 items for demo
//    }
//
//    @GetMapping(value = "/event-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
////    @PreAuthorize("hasAnyAuthority('endpoint/all')")
//    public Flux<String> streamSampleData() {
//        return streamSampleDataa(); // delegated to service
//    }
//}
//
//
