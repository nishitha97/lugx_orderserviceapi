package com.nishithadesilva.lugxgaming.orderserviceapi.service;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.CartItem;
import com.nishithadesilva.lugxgaming.orderserviceapi.domain.Game;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.CartItemDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.exception.ItemNotFoundException;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.CartItemRepository;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    private final GameRepository gameRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, GameRepository gameRepository) {
        this.cartItemRepository = cartItemRepository;
        this.gameRepository = gameRepository;
    }

    public CartItem createCart(CartItemDTO cartItemDTO) {

        Optional<Game> game = gameRepository.findById(UUID.fromString(cartItemDTO.getGameId()));

        if (game.isEmpty()) {
            throw new ItemNotFoundException("Game with id " + cartItemDTO.getGameId() + " not found.");
        }

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setGameId(cartItemDTO.getGameId());
        cartItem.setOrderId(null);

        CartItem cartItem1 = cartItemRepository.save(cartItem);
        return cartItem1;
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
