package com.example.bettingGame.core.converter;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.Team;
import com.example.bettingGame.core.dto.GameDto;
import com.example.bettingGame.core.dto.TeamDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameDtoConverter implements Converter<Game, GameDto> {

    @Override
    public GameDto convert(Game source) {
        return GameDto.builder()
                .id(source.getId())
                .date(source.getDate())
                .homeTeamId(source.getHomeTeamId())
                .awayTeamId(source.getAwayTeamId())
                .tourNumber(source.getTour())
                .build();
    }
}
