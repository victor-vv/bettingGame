package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.dto.TournamentDto;
import com.example.bettingGame.core.service.TournamentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/tournaments")
public class TournamentController {

    private TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public List<TournamentDto> getEnabledTournaments() {
        return tournamentService.getAllEnabledTournaments();
    }
}
