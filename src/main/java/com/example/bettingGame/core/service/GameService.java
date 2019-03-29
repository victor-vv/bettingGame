package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.Team;
import com.example.bettingGame.core.domain.Tournament;
import com.example.bettingGame.core.domain.custom.GameDetailsBean;
import com.example.bettingGame.core.dto.GameDto;
import com.example.bettingGame.core.dto.GameResponseDto;
import com.example.bettingGame.core.dto.TeamDto;
import com.example.bettingGame.core.repository.GameRepository;
import com.example.bettingGame.core.repository.TeamRepository;
import com.example.bettingGame.core.repository.TournamentRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private GameRepository gameRepository;
    private TeamRepository teamRepository;
    private TournamentRepository tournamentRepository;
    private ConversionService conversionService;

    public GameService(GameRepository gameRepository, TeamRepository teamRepository,
                       TournamentRepository tournamentRepository,
                       ConversionService conversionService) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
        this.conversionService = conversionService;
    }

    public List<GameResponseDto> getGameByTour(long tourNumber) {

        List<Game> games = gameRepository.findByTour(tourNumber);
        return games.stream()
                .map(game -> conversionService.convert(game, GameResponseDto.class))
                .collect(Collectors.toList());
    }

    public void createGame(GameDto gameDto) {
        // todo: check for duplicates (same teams with same date ???)
        // todo: logging ???
        Team homeTeam = teamRepository.findById(gameDto.getHomeTeamId()).orElseThrow(() -> new EntityNotFoundException("The home team does not exist"));
        Team awayTeam = teamRepository.findById(gameDto.getAwayTeamId()).orElseThrow(() -> new EntityNotFoundException("The away team does not exist"));
        Tournament tournament = tournamentRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("The home team does not exist"));
        Game game = Game.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .tournament(tournament)
                .date(gameDto.getDate())
                .finished(new Short("0"))
                .tour(gameDto.getTourNumber())
                .build();
        gameRepository.save(game);
    }
}
