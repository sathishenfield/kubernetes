package com.example.demo;

import com.example.demo.dto.Request1;
import com.example.demo.dto.Request2;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/aggregate")
@Slf4j
public class AggregateController {

    @GetMapping("/get/{path}")
    public ResponseEntity<String> getAggregate(@PathVariable("path") String path){
        log.info(path);
        return ResponseEntity.ok("Success Modifieddd");
    }

    @PostMapping("/post1")
    public ResponseEntity<Request1> postAggregate(@RequestBody Request1 request){
        return ResponseEntity.ok(request);
    }


    @PostMapping("/post2")
    public ResponseEntity<Request2> postAggregate2(@RequestBody Request2 request){
        System.out.println(request);
        return ResponseEntity.ok(request);
    }

    @PostMapping("/post3/{id}/{staticc}")
    public ResponseEntity<Request2> postAggregate3(@RequestBody Request2 request,@PathVariable("id") String id,@PathVariable("staticc") String staticc, @RequestParam("test") String test, @RequestParam("test2") String test2 ){
        System.out.println("Request Body : "+request);
        System.out.println("Path variable : "+id);
        System.out.println("Path variable static: "+staticc);
        System.out.println("Query param : "+test);
        System.out.println("Query param static : "+test2);
        return ResponseEntity.ok(request);
    }

//    @PostMapping("/post4")
//    public ResponseEntity<Request2> postAggregate4(@RequestBody Request2 request){
//        System.out.println("Request Body : "+request);
//        System.out.println("Path variable : "+demo);
//        return ResponseEntity.ok(request);
//    }

}
