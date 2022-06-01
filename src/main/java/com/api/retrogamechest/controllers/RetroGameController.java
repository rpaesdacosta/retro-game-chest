package com.api.retrogamechest.controllers;

import com.api.retrogamechest.dtos.RetroGameDto;
import com.api.retrogamechest.models.RetroGameModel;
import com.api.retrogamechest.services.RetroGameServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/retro-game")
public class RetroGameController {

    final RetroGameServiceImpl retroGameService;

    public RetroGameController(RetroGameServiceImpl retroGameService) {
        this.retroGameService = retroGameService;
    }

    @PostMapping
    public ResponseEntity<Object> registerRetroGame(@RequestBody @Valid RetroGameDto retroGameDto) {
        RetroGameModel retroGameModel = new RetroGameModel();
        BeanUtils.copyProperties(retroGameDto, retroGameModel);
        retroGameModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(retroGameService.save(retroGameModel));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Object> getOneRetroGame(@PathVariable(value = "id")UUID id) {
        Optional<RetroGameModel> retroGameModelOptional = retroGameService.findById(id);
        if (!retroGameModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Retro Game not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(retroGameModelOptional.get());
    }

    @GetMapping
    public  ResponseEntity<Page<RetroGameModel>> getAllRetroGames(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<RetroGameModel> retroGameList = retroGameService.findAll(pageable);
        if (retroGameList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(retroGameService.findAll(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRetroGame(@PathVariable(value = "id")UUID id) {
        Optional<RetroGameModel> retroGameModelOptional = retroGameService.findById(id);
        if (!retroGameModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Retro Game not found.");
        }
        retroGameService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Retro Game deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRetroGame(@PathVariable(value = "id")UUID id,
                                                  @RequestBody @Valid RetroGameDto retroGameDto) {
        Optional<RetroGameModel> retroGameModelOptional = retroGameService.findById(id);
        if (!retroGameModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Retro Game not found.");
        }
        RetroGameModel retroGameModel = retroGameModelOptional.get();
        retroGameModel.setName(retroGameDto.getName());
        retroGameModel.setPublisher(retroGameDto.getPublisher());
        retroGameModel.setPlatform(retroGameDto.getPlatform());
        retroGameModel.setDirector(retroGameDto.getDirector());
        retroGameModel.setReleaseDate(retroGameDto.getReleaseDate());

        return ResponseEntity.status(HttpStatus.OK).body(retroGameService.save(retroGameModel));
    }
}
