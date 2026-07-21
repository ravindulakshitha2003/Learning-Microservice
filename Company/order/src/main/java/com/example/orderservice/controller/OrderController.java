package com.example.orderservice.controller;

import com.example.orderservice.commen.OrderRespond;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.kafka.OrderPreducer;
import com.example.orderservice.service.OrderService;
import dto.OrderEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    private  OrderEventDTO orderEventDTO;
    private final OrderPreducer orderPreducer;

    public OrderController( OrderPreducer orderPreducer) {
        this.orderPreducer = orderPreducer;



    }


    @PostMapping
    public ResponseEntity<OrderRespond> create(@RequestBody OrderDto orderDto) {
        OrderRespond createdOrder = orderService.create(orderDto);

        OrderEventDTO orderEventDTO = new OrderEventDTO();
        orderEventDTO.setMessage("order comited");
        orderEventDTO.setStatus("pending");
        orderPreducer.sendMessage(orderEventDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAll() {
        List<OrderDto> orders = orderService.getAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable String id) {
        OrderDto order = orderService.getById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> update(@PathVariable String id, @RequestBody OrderDto orderDto) {
        OrderDto updatedOrder = orderService.update(id, orderDto);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
