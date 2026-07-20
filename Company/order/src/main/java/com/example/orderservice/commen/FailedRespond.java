package com.example.orderservice.commen;

import lombok.Data;

@Data
public class FailedRespond implements OrderRespond{
    private  final  String message;

    public FailedRespond(String message) {
        this.message = message;
    }
}
