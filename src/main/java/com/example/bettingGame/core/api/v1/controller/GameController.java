package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.dto.GameDto;
import com.example.bettingGame.core.service.GameService;
import com.example.bettingGame.core.service.TeamService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/game")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public GameDto getGameById(@RequestParam(required = false) long gameId) {
        return gameService.getGameById(gameId);
    }
}
