package com.example.orderservice.commen;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessRespond implements OrderRespond{
    private  final Order orderDto;

    public SuccessRespond(Order orderDto) {

        this.orderDto = orderDto;
    }


}
