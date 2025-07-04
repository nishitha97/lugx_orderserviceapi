package com.nishithadesilva.lugxgaming.orderserviceapi.dto;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.CartItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CartItemDTO {

    private UUID cartItemId;

    @NotNull
    private String gameId;

    @Min(1)
    private int quantity;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
