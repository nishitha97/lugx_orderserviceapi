package com.nishithadesilva.lugxgaming.orderserviceapi.service;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.CartItem;
import com.nishithadesilva.lugxgaming.orderserviceapi.domain.Game;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.CartItemDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.exception.ItemNotFoundException;

import com.nishithadesilva.lugxgaming.orderserviceapi.respository.CartItemRepository;
import com.nishithadesilva.lugxgaming.orderserviceapi.respository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private CartItemService cartItemService;

    @Test
    void createCart_shouldThrowException_whenGameNotFound() {
        CartItemDTO dto = new CartItemDTO();
        dto.setGameId(UUID.randomUUID().toString());
        dto.setQuantity(2);

        when(gameRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> cartItemService.createCart(dto));
    }

    @Test
    void createCart_shouldSaveCartItem_whenGameFound() {
        String gameId = UUID.randomUUID().toString();

        CartItemDTO dto = new CartItemDTO();
        dto.setGameId(gameId);
        dto.setQuantity(2);

        Game game = new Game();
        game.setGameId(UUID.fromString(gameId));

        CartItem expectedCartItem = new CartItem();
        expectedCartItem.setCartItemId(UUID.randomUUID());
        expectedCartItem.setGameId(gameId);
        expectedCartItem.setQuantity(2);

        when(gameRepository.findById(Mockito.any())).thenReturn(Optional.of(game));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(expectedCartItem);

        CartItem result = cartItemService.createCart(dto);

        assertNotNull(result);
        assertEquals(gameId, result.getGameId());
        assertEquals(2, result.getQuantity());
    }

    @Test
    void updateCart_shouldThrowException_whenCartItemNotFound() {
        CartItemDTO dto = new CartItemDTO();
        dto.setCartItemId(UUID.randomUUID());

        when(cartItemRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> cartItemService.updateCart(dto));
    }

    @Test
    void updateCart_shouldUpdateCartItem_whenCartItemExists() {
        UUID cartItemId = UUID.randomUUID();
        String gameId = UUID.randomUUID().toString();
        String orderId = UUID.randomUUID().toString();

        CartItemDTO dto = new CartItemDTO();
        dto.setCartItemId(cartItemId);
        dto.setGameId(gameId);
        dto.setQuantity(4);
        dto.setOrderId(orderId);

        CartItem existingItem = new CartItem();
        existingItem.setCartItemId(cartItemId);
        existingItem.setGameId("old-game-id");
        existingItem.setQuantity(1);
        existingItem.setOrderId(null);

        CartItem updatedItem = new CartItem();
        updatedItem.setCartItemId(cartItemId);
        updatedItem.setGameId(gameId);
        updatedItem.setQuantity(4);
        updatedItem.setOrderId(orderId);

        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(existingItem));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(updatedItem);

        CartItem result = cartItemService.updateCart(dto);

        assertNotNull(result);
        assertEquals(gameId, result.getGameId());
        assertEquals(4, result.getQuantity());
        assertEquals(orderId, result.getOrderId());
    }
}
