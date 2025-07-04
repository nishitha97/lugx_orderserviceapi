package com.nishithadesilva.lugxgaming.orderserviceapi.service;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.CartItem;
import com.nishithadesilva.lugxgaming.orderserviceapi.domain.Order;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.CartItemDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.GameDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.OrderDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.exception.BadRequestException;
import com.nishithadesilva.lugxgaming.orderserviceapi.helper.GameDetailsHelper;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.CartItemRepository;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final CartItemRepository cartItemRepository;

    private final CartItemService cartItemService;

    private final OrderRepository orderRepository;

    private final GameDetailsHelper gameDetailsHelper;

    @Value("${gameservice.api.url}")
    private String gameServiceApi;

    @Autowired
    public OrderService(CartItemRepository cartItemRepository, CartItemService cartItemService, OrderRepository orderRepository, GameDetailsHelper gameDetailsHelper) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemService = cartItemService;
        this.orderRepository = orderRepository;
        this.gameDetailsHelper = gameDetailsHelper;
    }

    public Order createOrder(OrderDTO orderDTO) throws Exception {

        if (orderDTO.getTotalPrice() != null) {
            throw new BadRequestException("TotalPrice is not allowed be set.");
        }

        Order order = new Order();
        order.setOrderId(UUID.randomUUID());
        order.setOrderDateTime(orderDTO.getOrderDateTime());
        order.setCustomerId(orderDTO.getCustomerId());
        order.setCartItemIds(orderDTO.getCartItemIds());

        BigDecimal total = BigDecimal.ZERO;

        for (String cartItemId : orderDTO.getCartItemIds()) {

            Optional<CartItem> optionalCartItem = cartItemRepository.findById(UUID.fromString(cartItemId));

            if (optionalCartItem.isEmpty()) {
                throw new Exception("Failed to place order. Invalid cartItemId provided :" + cartItemId);
            }

            CartItem cartItem = optionalCartItem.get();

            GameDTO game = gameDetailsHelper.getGameDetails(cartItem.getGameId());

            if (game != null) {
                total = total.add(game.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            }

            cartItem.setOrderId(order.getOrderId().toString());
            cartItemService.updateCart(new CartItemDTO(cartItem));

        }
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }
}
