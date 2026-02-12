package com.example.payment.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Payment!";
    }
}
