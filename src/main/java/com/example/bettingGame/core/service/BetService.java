package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Bet;
import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.dto.BetDto;
import com.example.bettingGame.core.repository.BetRepository;
import com.example.bettingGame.core.repository.GameRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class BetService {

    private BetRepository betRepository;
    private GameRepository gameRepository;

    public BetService(BetRepository betRepository, GameRepository gameRepository) {
        this.betRepository = betRepository;
        this.gameRepository = gameRepository;
    }



    public void createBet(BetDto betDto) {
        // todo: check for duplicates (same bet ???)
        // todo: logging ???
        // todo: check that game is not finished
        Game game = gameRepository.findById(betDto.getGameId()).orElseThrow(() -> new EntityNotFoundException("Game doesn't exist"));
        Bet bet = Bet.builder()
                .game(game)
                .homeTeamScore(betDto.getHomeTeamScore())
                .awayTeamScore(betDto.getAwayTeamScore())
                .user(betDto.getUserId())
                .build();
        betRepository.save(bet);
    }
}
