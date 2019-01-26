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
                .homeTeam(buildTeamDto(source.getHomeTeam()))
                .awayTeam(buildTeamDto(source.getAwayTeam()))
                .finished(source.getFinished() == 1)
                .tournament(source.getTournament().getName())
                .build();
    }

    private TeamDto buildTeamDto(Team team) {
        return TeamDto.builder().id(team.getId())
                .name(team.getName())
                .city(team.getCity())
                .build();
    }
}
