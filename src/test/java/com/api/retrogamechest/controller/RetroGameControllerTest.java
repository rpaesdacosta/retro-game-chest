package com.api.retrogamechest.controller;

import com.api.retrogamechest.controllers.RetroGameController;
import com.api.retrogamechest.models.RetroGameModel;
import com.api.retrogamechest.services.RetroGameServiceImpl;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;

@WebMvcTest
public class RetroGameControllerTest {

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private RetroGameController retroGameController;

    @MockBean
    private RetroGameServiceImpl retroGameServiceImpl;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(this.retroGameController);
        standaloneMockMvcBuilder.setCustomArgumentResolvers(pageableArgumentResolver);
        standaloneSetup(standaloneMockMvcBuilder);
    }

    private void configureWhenFindByIdThenReturn(Optional optional) {
        Mockito.when(this.retroGameServiceImpl.findById(UUID.fromString("0747b817-3509-429b-97e0-24daedcc5171")))
                .thenReturn(optional);
    }

    private void configureWhenFindAllThenReturn(List<RetroGameModel> list) {
        Mockito.when(this.retroGameServiceImpl.findAll())
                .thenReturn(list);
    }

    @Test
    public void shouldReturnSuccess_WhenRegisterRetroGame() {

        JSONObject request = new JSONObject();
        request.put("name", "Retro Game Test");
        request.put("publisher", "Publisher Test");
        request.put("platform", "Platform Test");
        request.put("director", "Director Test");
        request.put("releaseDate", "1994-04-26");

        given()
                .contentType(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post("/retro-game")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnBadRequest_WhenRegisterRetroGame() {

        given()
                .contentType(ContentType.JSON)
                .body(new JSONObject().toJSONString())
                .when()
                .post("/retro-game")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturnSuccess_WhenGetOneRetroGame() {

        configureWhenFindByIdThenReturn(Optional.of(new RetroGameModel()));

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/retro-game/{id}", UUID.fromString("0747b817-3509-429b-97e0-24daedcc5171"))
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturnNotFound_WhenGetOneRetroGame() {

        configureWhenFindByIdThenReturn(Optional.empty());

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/retro-game/{id}", UUID.fromString("0747b817-3509-429b-97e0-24daedcc5171"))
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnSuccess_WhenGetAllRetroGames() {

        List<RetroGameModel> list = new ArrayList<>();
        list.add(new RetroGameModel());

        configureWhenFindAllThenReturn(list);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/retro-game?page=0&size=1")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturnNotFound_WhenGetAllRetroGames() {

        List<RetroGameModel> list = new ArrayList<>();
        configureWhenFindAllThenReturn(list);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/retro-game?page=0&size=1")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnSuccess_WhenDeleteRetroGame() {

        configureWhenFindByIdThenReturn(Optional.of(new RetroGameModel()));

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/retro-game/{id}", UUID.fromString("0747b817-3509-429b-97e0-24daedcc5171"))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturnNotFound_WhenDeleteRetroGame() {

        configureWhenFindByIdThenReturn(Optional.empty());

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/retro-game/{id}", UUID.fromString("0747b817-3509-429b-97e0-24daedcc5171"))
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnSuccess_WhenUpdateRetroGame() {

        configureWhenFindByIdThenReturn(Optional.of(new RetroGameModel()));

        JSONObject request = new JSONObject();
        request.put("name", "Retro Game Test");
        request.put("publisher", "Publisher Test");
        request.put("platform", "Platform Test");
        request.put("director", "Director Test");
        request.put("releaseDate", "1994-04-26");

        given()
                .contentType(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/retro-game/0747b817-3509-429b-97e0-24daedcc5171")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturnNotFound_WhenUpdateRetroGame() {

        configureWhenFindByIdThenReturn(Optional.empty());

        JSONObject request = new JSONObject();
        request.put("name", "Retro Game Test");
        request.put("publisher", "Publisher Test");
        request.put("platform", "Platform Test");
        request.put("director", "Director Test");
        request.put("releaseDate", "1994-04-26");

        given()
                .contentType(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/retro-game/0747b817-3509-429b-97e0-24daedcc5171")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
