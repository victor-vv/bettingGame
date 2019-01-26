package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.dto.GameDto;
import com.example.bettingGame.core.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
