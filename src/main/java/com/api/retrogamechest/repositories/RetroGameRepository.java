package com.api.retrogamechest.repositories;

import com.api.retrogamechest.models.RetroGameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RetroGameRepository extends JpaRepository<RetroGameModel, UUID> {
}
