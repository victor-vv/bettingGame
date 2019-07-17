package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Bet;
import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.Team;
import com.example.bettingGame.core.domain.Tournament;
import com.example.bettingGame.core.dto.GameDto;
import com.example.bettingGame.core.dto.GameResponseDto;
import com.example.bettingGame.core.repository.BetRepository;
import com.example.bettingGame.core.repository.GameRepository;
import com.example.bettingGame.core.repository.TeamRepository;
import com.example.bettingGame.core.repository.TournamentRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private GameRepository gameRepository;
    private TeamRepository teamRepository;
    private BetRepository betRepository;
    private TournamentRepository tournamentRepository;
    private ConversionService conversionService;

    public GameService(GameRepository gameRepository, TeamRepository teamRepository,
                       BetRepository betRepository, TournamentRepository tournamentRepository,
                       ConversionService conversionService) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.betRepository = betRepository;
        this.tournamentRepository = tournamentRepository;
        this.conversionService = conversionService;
    }

    @Transactional
    public List<GameResponseDto> getGamesByTour(String tourNumber, long userId) {

        List<Game> games = gameRepository.findByTour(tourNumber);
        return games.stream()
                .map(game -> convert(game, userId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createGame(GameDto gameDto) {
        // todo: check for duplicates (same teams with same date ???)
        // todo: logging ???
        Team homeTeam = teamRepository.findById(gameDto.getHomeTeamId()).orElseThrow(() -> new EntityNotFoundException("Home team does not exist"));
        Team awayTeam = teamRepository.findById(gameDto.getAwayTeamId()).orElseThrow(() -> new EntityNotFoundException("Away team does not exist"));
        Tournament tournament = tournamentRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Tournament does not exist"));
        Game game = Game.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .tournament(tournament)
                .date(gameDto.getDate())
                .finished(false)
                .tourId(gameDto.getTour())
                .build();
        gameRepository.save(game);
    }

    @Transactional
    //TODO: перенести в правильный сервис и сделать правильно, без перебора всех игр
    public List<Long> getTours(long tournamentId) {
        List<Game> games = gameRepository.findByTournamentId(tournamentId);
        return games.stream()
                .map(Game::getTourId)
                .distinct()
                .collect(Collectors.toList());
    }

    private GameResponseDto convert(Game game, long userId) {
        GameResponseDto response = conversionService.convert(game, GameResponseDto.class);
        betRepository.findByGameIdAndUserId(game.getId(), userId).ifPresent(bet -> addBetDetails(response, bet));
        response.setUserId(userId);
        return response;
    }

    private GameResponseDto addBetDetails(GameResponseDto response, Bet bet) {
        response.setHomeTeamBet(bet.getHomeTeamScore());
        response.setAwayTeamBet(bet.getAwayTeamScore());
        return response;
    }
}