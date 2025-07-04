package com.nishithadesilva.lugxgaming.orderserviceapi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
@Table(name = "cartItem")
public class CartItem {

    @Id
    @GeneratedValue(generator = "UUID")
    //@Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID cartItemId;

    @NotBlank(message = "Game ID is required.")
    private String gameId;

    @NotBlank(message = "Quantity ID is required.")
    private int quantity;

    private String orderId;

    public UUID getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(UUID cartItemId) {
        this.cartItemId = cartItemId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
