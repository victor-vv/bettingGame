package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.dto.UserRankingResponseDto;
import com.example.bettingGame.core.service.UserScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/userRanking")
public class UserRankingController {

    private UserScoreService userScoreService;

    public UserRankingController(UserScoreService userScoreService) {
        this.userScoreService = userScoreService;
    }

    @GetMapping
    public UserRankingResponseDto getUserRankingForTournament(@RequestParam long tournamentId) {
        return userScoreService.getUserRankingForTournament(tournamentId);
    }

    @GetMapping(value = "/tours")
    public UserRankingResponseDto getUserRankingTourForTournament(@RequestParam long tournamentId) {
        return userScoreService.getUserRankingTourForTournament(tournamentId);
    }
}
