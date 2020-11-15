package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Bet;
import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.User;
import com.example.bettingGame.core.dto.BetDto;
import com.example.bettingGame.core.repository.BetRepository;
import com.example.bettingGame.core.repository.GameRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BetService {

    private BetRepository betRepository;
    private GameRepository gameRepository;

    public BetService(BetRepository betRepository, GameRepository gameRepository) {
        this.betRepository = betRepository;
        this.gameRepository = gameRepository;
    }



    public void createBet(BetDto betDto, User user) {
        // todo: logging ???
        // todo: check that game is not finished (validator!)
        long gameId = betDto.getGameId();
        long userId = user.getId();
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game doesn't exist"));
        Bet bet = betRepository.findByGameIdAndUserId(gameId, userId).orElse(new Bet());
        bet.setGame(game);
        bet.setHomeTeamScore(betDto.getHomeTeamScore());
        bet.setAwayTeamScore(betDto.getAwayTeamScore());
        bet.setUserId(userId);
        betRepository.save(bet);
    }

    public List<Bet> getBetsForGame(long gameId) {
        return betRepository.findAllByGameId(gameId);
    }

//    public UserRankingResponseDto getUserScoresForTournament(long tournamentId) {
//
//    }
}
