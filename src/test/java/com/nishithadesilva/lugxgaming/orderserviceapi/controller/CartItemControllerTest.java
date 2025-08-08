package com.nishithadesilva.lugxgaming.orderserviceapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nishithadesilva.lugxgaming.orderserviceapi.domain.CartItem;
import com.nishithadesilva.lugxgaming.orderserviceapi.dto.CartItemDTO;
import com.nishithadesilva.lugxgaming.orderserviceapi.service.CartItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartItemController.class)
class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartItemService cartItemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder_shouldReturn200_whenSuccessful() throws Exception {

        CartItemDTO dto = new CartItemDTO();
        dto.setGameId(UUID.randomUUID().toString());
        dto.setQuantity(2);

        CartItem savedItem = new CartItem();
        savedItem.setCartItemId(UUID.randomUUID());
        savedItem.setGameId(dto.getGameId());
        savedItem.setQuantity(dto.getQuantity());

        when(cartItemService.createCart(any(CartItemDTO.class))).thenReturn(savedItem);

        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(dto.getGameId()))
                .andExpect(jsonPath("$.quantity").value(dto.getQuantity()));
    }
}
