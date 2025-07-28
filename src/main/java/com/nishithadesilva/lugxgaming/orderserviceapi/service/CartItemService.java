package com.nishithadesilva.lugxgaming.orderserviceapi.service;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.CartItem;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.CartItemDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.GameDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.exception.ItemNotFoundException;
import com.nishithadesilva.lugxgaming.orderserviceapi.helper.GameDetailsHelper;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    private final GameDetailsHelper gameDetailsHelper;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, GameDetailsHelper gameDetailsHelper) {
        this.cartItemRepository = cartItemRepository;
        this.gameDetailsHelper = gameDetailsHelper;
    }

    public CartItem createCart(CartItemDTO cartItemDTO) {

        GameDTO gameDTO = gameDetailsHelper.getGameDetails(cartItemDTO.getGameId());

        if (gameDTO == null) {
            throw new ItemNotFoundException("Game with id " + cartItemDTO.getGameId() + " not found.");
        }

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setGameId(cartItemDTO.getGameId());
        cartItem.setOrderId(null);

        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCart(CartItemDTO cartItemDTO) {

        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemDTO.getCartItemId());

        if (optionalCartItem.isEmpty()) {
            throw new ItemNotFoundException("CartItem with id " + cartItemDTO.getGameId() + " not found.");
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
