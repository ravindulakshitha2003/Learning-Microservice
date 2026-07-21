package com.example.orderservice.commen;


import com.example.orderservice.entity.Order;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;


@Data

public class SuccessRespond implements OrderRespond{
    @JsonUnwrapped
    private  final Order orderDto;

    public SuccessRespond(Order orderDto) {

        this.orderDto = orderDto;
    }



}
