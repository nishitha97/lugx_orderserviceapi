package com.nishithadesilva.lugxgaming.orderserviceapi.controller;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.Order;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.OrderDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController (OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        try {
            Order createdOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to create order: " + e.getMessage());
        }
    }
}