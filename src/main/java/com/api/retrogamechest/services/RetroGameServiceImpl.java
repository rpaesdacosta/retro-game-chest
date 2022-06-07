package com.api.retrogamechest.services;

import com.api.retrogamechest.models.RetroGameModel;
import com.api.retrogamechest.repositories.RetroGameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RetroGameServiceImpl implements RetroGameService {

    final RetroGameRepository retroGameRepository;

    public RetroGameServiceImpl(RetroGameRepository retroGameRepository) {
        this.retroGameRepository = retroGameRepository;
    }

    @Override
    @Transactional
    public RetroGameModel save(RetroGameModel retroGameModel) {
        return retroGameRepository.save(retroGameModel);
    }

    @Override
    public Optional<RetroGameModel> findById(UUID retroGameId) {
        return retroGameRepository.findById(retroGameId);
    }

    @Override
    public List<RetroGameModel> findAll() {
        return retroGameRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(UUID retroGameId) {
        retroGameRepository.deleteById(retroGameId);
    }
}
