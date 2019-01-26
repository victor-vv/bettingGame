package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.Team;
import com.example.bettingGame.core.domain.custom.GameDetailsBean;
import com.example.bettingGame.core.dto.GameDto;
import com.example.bettingGame.core.dto.TeamDto;
import com.example.bettingGame.core.repository.GameRepository;
import com.example.bettingGame.core.repository.TeamRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class GameService {

    private GameRepository gameRepository;
    private ConversionService conversionService;

    public GameService(GameRepository gameRepository, ConversionService conversionService) {
        this.gameRepository = gameRepository;
        this.conversionService = conversionService;
    }

    public GameDto getGameById(long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);
        return conversionService.convert(game, GameDto.class);
    }
}
