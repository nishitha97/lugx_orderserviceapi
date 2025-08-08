package com.nishithadesilva.lugxgaming.orderserviceapi.dto;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.CartItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class CartItemDTO {

    private UUID cartItemId;

    @NotBlank(message = "Game ID is required.")
    private String gameId;

    @Positive(message = "Quantity must be a positive number.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    @NotNull
    private Integer quantity;

    private String orderId;

    public CartItemDTO() {
    }

    public CartItemDTO(CartItem cartItem) {
        this.cartItemId = cartItem.getCartItemId();
        this.gameId = cartItem.getGameId();
        this.quantity = cartItem.getQuantity();

        if (cartItem.getOrderId() != null) {
            this.orderId = cartItem.getOrderId();
        }
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UUID getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(UUID cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
