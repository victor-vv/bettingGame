package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.dto.TourDto;
import com.example.bettingGame.core.repository.GameRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourService {

    private GameRepository gameRepository;
    private ConversionService conversionService;

    public TourService(GameRepository gameRepository, ConversionService conversionService) {
        this.gameRepository = gameRepository;
        this.conversionService = conversionService;
    }

    @Transactional
    public List<TourDto> getTours(long tournamentId) {
        List<Game> games = gameRepository.findByTournamentId(tournamentId);
        return games.stream()
                .map(Game::getTour)
                .distinct()
                .map(tour -> conversionService.convert(tour, TourDto.class))
                .collect(Collectors.toList());
    }
}