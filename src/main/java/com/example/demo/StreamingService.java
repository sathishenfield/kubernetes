//package com.example.demo;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.DirectProcessor;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.FluxProcessor;
//import reactor.core.publisher.FluxSink;
//
//@Service
//public class StreamingService {
//
//
//    private final WebClient webClient = WebClient.builder()
//            .baseUrl("https://api.osint.industries")
//            .defaultHeader(HttpHeaders.ACCEPT, "application/json")
//            .defaultHeader("api-key", "cefa312f9b41bd995a3f820be08e25e9") // Replace
//            .build();
//
//    private final FluxProcessor<String, String> processor;
//    private final FluxSink<String> sink;
//
//    public StreamingService() {
//        this.processor = DirectProcessor.<String>create().serialize();
//        this.sink = processor.sink();
//
//        startStream();
//    }
//
//    private void startStream() {
//        webClient.get()
//                .retrieve()
//                .bodyToFlux(String.class)
//                .doOnNext(data -> System.out.println("Received from 3rd party stream: " + data))
//                .subscribe(sink::next, error -> {
//                    System.out.println("Stream error: " + error.getMessage());
//                    sink.error(error);
//                });
//    }
//
//    public Flux<String> getStreamedData() {    return processor;}
//
//}
