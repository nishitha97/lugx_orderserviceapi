package com.nishithadesilva.lugxgaming.orderserviceapi.controller;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.Order;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.OrderDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO) throws Exception {
        Order createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(createdOrder);
    }
}