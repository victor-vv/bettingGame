package com.example.bettingGame.core.converter;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.Team;
import com.example.bettingGame.core.domain.Tour;
import com.example.bettingGame.core.dto.GameResponseDto;
import com.example.bettingGame.core.dto.TeamDto;
import com.example.bettingGame.core.dto.TourDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TourDtoConverter implements Converter<Tour, TourDto> {

    @Override
    public TourDto convert(Tour source) {
        if (source == null) {
            return null;
        }

        return TourDto.builder()
                .id(source.getId())
                .name(source.getName())
                .deadline(source.getDeadline())
                .dateFrom(source.getDateFrom())
                .dateUntil(source.getDateUntil())
                .tournamentId(source.getTournamentId())
                .build();
    }
}
