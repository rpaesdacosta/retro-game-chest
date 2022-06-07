package com.api.retrogamechest.services;

import com.api.retrogamechest.models.RetroGameModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RetroGameService {

    RetroGameModel save(RetroGameModel retroGameModel);
    Optional<RetroGameModel> findById(UUID retroGameId);
    List<RetroGameModel> findAll();
    void delete(UUID retroGameId);
}
