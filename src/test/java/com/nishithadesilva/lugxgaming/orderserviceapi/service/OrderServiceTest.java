package com.nishithadesilva.lugxgaming.orderserviceapi.service;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.CartItem;
import com.nishithadesilva.lugxgaming.orderserviceapi.domain.Game;
import com.nishithadesilva.lugxgaming.orderserviceapi.domain.Order;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.OrderDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.exception.BadRequestException;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.CartItemRepository;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.GameRepository;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private CartItemRepository cartItemRepository;
    @Mock private CartItemService cartItemService;
    @Mock private OrderRepository orderRepository;
    @Mock private GameRepository gameRepository;

    @InjectMocks private OrderService orderService;

    @Test
    void createOrder_shouldThrowBadRequest_whenTotalPriceIsSet() {
        OrderDTO dto = new OrderDTO();
        dto.setTotalPrice(BigDecimal.valueOf(100.00));

        assertThrows(BadRequestException.class, () -> orderService.createOrder(dto));
    }

    @Test
    void createOrder_shouldThrowException_whenInvalidCartItemIdProvided() {
        String invalidCartItemId = UUID.randomUUID().toString();

        OrderDTO dto = new OrderDTO();
        dto.setCustomerId("cust123");
        dto.setOrderDateTime(LocalDateTime.now());
        dto.setCartItemIds(List.of(invalidCartItemId));

        when(cartItemRepository.findById(UUID.fromString(invalidCartItemId)))
                .thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> orderService.createOrder(dto));
        assertTrue(ex.getMessage().contains("Invalid cartItemId provided"));
    }

    @Test
    void createOrder_shouldSucceed_whenValidDataProvided() throws Exception {
        UUID cartItemId = UUID.randomUUID();
        String gameId = UUID.randomUUID().toString();

        OrderDTO dto = new OrderDTO();
        dto.setCustomerId("cust123");
        dto.setOrderDateTime(LocalDateTime.now());
        dto.setCartItemIds(List.of(cartItemId.toString()));

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(cartItemId);
        cartItem.setGameId(gameId);
        cartItem.setQuantity(2);

        Game game = new Game();
        game.setGameId(UUID.fromString(gameId));
        game.setPrice(BigDecimal.valueOf(30));

        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItem));
        when(gameRepository.findById(Mockito.any())).thenReturn(Optional.of(game));
        when(cartItemService.updateCart(any())).thenReturn(cartItem);

        Order savedOrder = new Order();
        UUID orderId = UUID.randomUUID();
        savedOrder.setOrderId(orderId);
        savedOrder.setCustomerId("cust123");
        savedOrder.setCartItemIds(dto.getCartItemIds());
        savedOrder.setOrderDateTime(dto.getOrderDateTime());
        savedOrder.setTotalPrice(BigDecimal.valueOf(60)); // Expected total

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        Order result = orderService.createOrder(dto);

        assertNotNull(result);
        assertEquals("cust123", result.getCustomerId());
        assertEquals(BigDecimal.valueOf(60), result.getTotalPrice());
        assertEquals(1, result.getCartItemIds().size());
        assertEquals(cartItemId.toString(), result.getCartItemIds().get(0));
    }
}
