package com.nishithadesilva.lugxgaming.orderserviceapi.helper;

import com.nishithadesilva.lugxgaming.orderserviceapi.dto.GameDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GameDetailsHelper {

    private final RestTemplate restTemplate;

    @Value("${gameservice.api.url}")
    private String gameServiceApi;

    public GameDetailsHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GameDTO getGameDetails(String gameId) {

        ResponseEntity<GameDTO> response = restTemplate.getForEntity(gameServiceApi + gameId, GameDTO.class);
        return response.getBody();
    }
}
