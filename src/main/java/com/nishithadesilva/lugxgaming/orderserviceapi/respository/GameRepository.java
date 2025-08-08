package com.nishithadesilva.lugxgaming.orderserviceapi.respository;

import com.nishithadesilva.lugxgaming.orderserviceapi.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
}