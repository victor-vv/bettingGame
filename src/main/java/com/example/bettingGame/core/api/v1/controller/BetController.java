package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.domain.User;
import com.example.bettingGame.core.dto.BetDto;
import com.example.bettingGame.core.dto.GameDto;
import com.example.bettingGame.core.dto.GameResponseDto;
import com.example.bettingGame.core.service.BetService;
import com.example.bettingGame.core.service.GameService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/bets")
public class BetController {

    private BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully saved"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public void createBet(@RequestBody @Valid BetDto betDto, @AuthenticationPrincipal User user) {
        betService.createBet(betDto, user);
    }
}
