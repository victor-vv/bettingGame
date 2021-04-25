package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.dto.UserRankingResponseDto;
import com.example.bettingGame.core.service.UserScoreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/userRankings")
public class UserRankingController {

    private final UserScoreService userScoreService;

    public UserRankingController(UserScoreService userScoreService) {
        this.userScoreService = userScoreService;
    }

    @CrossOrigin
    @GetMapping("/tournaments/{tournamentId}")
    public UserRankingResponseDto getUserRankingForTournament(@PathVariable long tournamentId) {
        return userScoreService.getUserRankingForTournament(tournamentId);
    }

    //TODO: add medals in profiles

//    @GetMapping(value = "/tours")
//    public UserRankingResponseDto getUserRankingTourForTournament(@RequestParam long tournamentId) {
//        return userScoreService.getUserRankingTourForTournament(tournamentId);
//    }
}
