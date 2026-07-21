package com.example.orderservice.service;

import com.example.inventoryservice.dto.InventoryDto;
import com.example.orderservice.commen.FailedRespond;
import com.example.orderservice.commen.OrderRespond;
import com.example.orderservice.commen.SuccessRespond;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private  final WebClient webClient;

    public OrderService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public OrderRespond create(OrderDto orderDto) {
//        call the inventory
        Integer id = Integer.valueOf(orderDto.getId());
        try{
            InventoryDto data = webClient.get()
                    .uri("http://apigatway/api/inventory/{id}", id)
                    .retrieve()
                    .bodyToMono(InventoryDto.class)
                    .block();

            System.out.println(data);
            assert data != null;
            if(data.getAvailableQuantity()>0){
                 Order order = new Order();
                 order.setCustomerName(orderDto.getCustomerName());
                 order.setProductName(orderDto.getProductName());
                 order.setQuantity(orderDto.getQuantity());
                 order.setTotalPrice(orderDto.getTotalPrice());

                 Order savedOrder = orderRepository.save(order);

                 return  new SuccessRespond(savedOrder);

             }else{
                    return  new FailedRespond("Order Not Found");
            }
        } catch (Exception e) {

            return  new FailedRespond(e.getMessage());
        }





    }

    public List<OrderDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public OrderDto getById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return mapToDto(order);
    }

    public OrderDto update(String id, OrderDto orderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setCustomerName(orderDto.getCustomerName());
        order.setProductName(orderDto.getProductName());
        order.setQuantity(orderDto.getQuantity());
        order.setTotalPrice(orderDto.getTotalPrice());

        Order updatedOrder = orderRepository.save(order);

        return mapToDto(updatedOrder);
    }

    public void delete(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepository.deleteById(order.getId());
    }

    private OrderDto mapToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setProductName(order.getProductName());
        dto.setQuantity(order.getQuantity());
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }

}
