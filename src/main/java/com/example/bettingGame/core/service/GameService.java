package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Bet;
import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.Team;
import com.example.bettingGame.core.dto.GameDto;
import com.example.bettingGame.core.dto.GameResponseDto;
import com.example.bettingGame.core.dto.GameScoreDto;
import com.example.bettingGame.core.repository.BetRepository;
import com.example.bettingGame.core.repository.GameRepository;
import com.example.bettingGame.core.repository.TeamRepository;
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
    private BetService betService;
    private UserScoreService userScoreService;
    private ConversionService conversionService;

    public GameService(GameRepository gameRepository, TeamRepository teamRepository,
                       BetRepository betRepository,
                       BetService betService, UserScoreService userScoreService, ConversionService conversionService) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.betRepository = betRepository;
        this.betService = betService;
        this.userScoreService = userScoreService;
        this.conversionService = conversionService;
    }

    @Transactional
    public List<GameResponseDto> getGamesByTour(long tourId, long userId) {

        List<Game> games = gameRepository.findByTourId(tourId);
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
        Game game = Game.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .date(gameDto.getDate())
                .finished(false)
                .tourId(gameDto.getTour())
                .build();
        gameRepository.save(game);
    }

    @Transactional
    public void addScore(GameScoreDto gameScoreDto) {
        Game game = gameRepository.findById(gameScoreDto.getGameId()).orElseThrow(() -> new EntityNotFoundException("Game not found"));
        game.setHomeTeamScore(gameScoreDto.getHomeTeamScore());
        game.setAwayTeamScore(gameScoreDto.getAwayTeamScore());
    }


    public void closeGamesForTour(long tourId) {
        List<Game> games = gameRepository.findByTourId(tourId);
        games.forEach(game -> closeGame(game.getId()));
    }

    /**
     * Checks all the bets for the given game, calculates and stores user scores
     */
    public void closeGame(long gameId) {
        List<Bet> betsForGame = betService.getBetsForGame(gameId);
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game not found"));
        betsForGame.forEach(bet -> userScoreService.closeBet(bet, game));

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