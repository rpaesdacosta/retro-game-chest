package com.api.retrogamechest.controllers;

import com.api.retrogamechest.dtos.RetroGameDto;
import com.api.retrogamechest.models.RetroGameModel;
import com.api.retrogamechest.services.RetroGameServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        } else {
            RetroGameDto retroGameDto = new RetroGameDto();
            BeanUtils.copyProperties(retroGameModelOptional.get(), retroGameDto);
            retroGameModelOptional.get().add(linkTo(methodOn(RetroGameController.class).getAllRetroGames(Pageable.ofSize(10).withPage(0))).withRel("Retro Game Full List"));
            return ResponseEntity.status(HttpStatus.OK).body(retroGameModelOptional.get());
        }
    }

    @GetMapping
    public  ResponseEntity<Page<RetroGameModel>> getAllRetroGames(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<RetroGameModel> retroGameList = retroGameService.findAll();
        if (retroGameList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (RetroGameModel retroGame : retroGameList) {
                retroGame.add(linkTo(methodOn(RetroGameController.class).getOneRetroGame(retroGame.getId())).withSelfRel());
            }
        }

        List<RetroGameModel> pageList = retroGameList.stream()
                .skip(pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        Page retroGamePage = new PageImpl<>(pageList, pageable, retroGameList.size());

        return ResponseEntity.status(HttpStatus.OK).body(retroGamePage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRetroGame(@PathVariable(value = "id")UUID id) {
        Optional<RetroGameModel> retroGameModelOptional = retroGameService.findById(id);
        if (!retroGameModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Retro Game not found.");
        }
        retroGameService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
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
