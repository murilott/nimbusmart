package com.example.order.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1/order")
@AllArgsConstructor
public class OrderController {
    
    @GetMapping
    public String hello() {
        return "Hello order!";
    }
    
}
