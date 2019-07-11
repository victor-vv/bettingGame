package com.example.bettingGame.core.service;

import com.example.bettingGame.core.dto.TournamentDto;
import com.example.bettingGame.core.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    private TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Transactional
    public List<TournamentDto> getAllEnabledTournaments() {
        return tournamentRepository.findAll().stream()
                .map(tournament -> new TournamentDto(tournament.getId(), tournament.getName()))
                .collect(Collectors.toList());
    }
}
