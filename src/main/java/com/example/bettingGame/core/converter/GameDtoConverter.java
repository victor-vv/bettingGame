package com.example.bettingGame.core.converter;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.dto.GameDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//TODO: remove, not needed. GameResponseDtoConverter instead
@Component
public class GameDtoConverter implements Converter<Game, GameDto> {

    @Override
    public GameDto convert(Game source) {
        return GameDto.builder()
                .id(source.getId())
                .date(source.getDate())
                .homeTeamId(source.getHomeTeamId())
                .awayTeamId(source.getAwayTeamId())
                .tour(source.getTour())
                .build();
    }
}
