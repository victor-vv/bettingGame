package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.domain.User;
import com.example.bettingGame.core.dto.GameDto;
import com.example.bettingGame.core.dto.GameResponseDto;
import com.example.bettingGame.core.service.GameService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/games")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<GameResponseDto> getGamesByTour(@AuthenticationPrincipal User user, @RequestParam long tournamentId, @RequestParam long tourId) {
/// TODO: Доделать поиск по турниру и туру, чтобы они сначала подгружались на экран, а потом по их айдишникам подгружались игры
        return gameService.getGamesByTour(tourName, user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully saved"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public void createGame(@RequestBody GameDto gameDto) {
        gameService.createGame(gameDto);
    }
}
