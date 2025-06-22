package com.nishithadesilva.lugxgaming.orderserviceapi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

import java.util.UUID;

@Entity
@Table(name = "cartItem")
public class CartItem {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID cartItemId;

    private String gameId;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
