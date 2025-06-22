package com.nishithadesilva.lugxgaming.orderserviceapi.service;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.CartItem;
import com.nishithadesilva.lugxgaming.orderserviceapi.domain.Order;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.CartItemDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.GameDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.OrderDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.CartItemRepository;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final CartItemRepository cartItemRepository;

    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;

    @Value("${gameservice.api.url}")
    private String gameServiceApi;

    @Autowired
    public OrderService(CartItemRepository cartItemRepository, OrderRepository orderRepository, RestTemplate restTemplate) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID());
        order.setOrderDateTime(orderDTO.getOrderDateTime());
        order.setCustomerId(orderDTO.getCustomerId());

        List<CartItem> cartItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItemDTO itemDTO : orderDTO.getItems()) {

            GameDTO game = getGameDetails(itemDTO.getGameId());

            if (game != null) {
                CartItem item = new CartItem();
                item.setGameId(game.getGameId().toString());
                item.setQuantity(itemDTO.getQuantity());
                item.setOrder(order);

                total = total.add(game.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));

                cartItems.add(item);
            }
        }

        order.setItems(cartItems);
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }

    private GameDTO getGameDetails(String gameId) {

        ResponseEntity<GameDTO> response = restTemplate.getForEntity(gameServiceApi + gameId, GameDTO.class);
        return response.getBody();
    }
}
