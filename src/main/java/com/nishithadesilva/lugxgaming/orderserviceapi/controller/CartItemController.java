package com.nishithadesilva.lugxgaming.orderserviceapi.controller;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.CartItem;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.CartItemDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid CartItemDTO cartItemDTO) throws Exception{
        CartItem cartItem = cartItemService.createCart(cartItemDTO);
        return ResponseEntity.ok(cartItem);
    }
}
