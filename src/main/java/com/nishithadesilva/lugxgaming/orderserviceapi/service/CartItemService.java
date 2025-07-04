package com.nishithadesilva.lugxgaming.orderserviceapi.service;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.CartItem;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.CartItemDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem createCart(CartItemDTO cartItemDTO) {

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setGameId(cartItemDTO.getGameId());
        cartItem.setOrderId(null);

        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCart(CartItemDTO cartItemDTO) {

        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemDTO.getCartItemId());

        if (optionalCartItem.isEmpty()) {
            throw new RuntimeException("CartItem not found");
        }

        CartItem cartItem = optionalCartItem.get();

        if (cartItemDTO.getOrderId() != null) {
            cartItem.setOrderId(cartItem.getOrderId());
        }
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setGameId(cartItemDTO.getGameId());

        return cartItemRepository.save(cartItem);
    }
}
