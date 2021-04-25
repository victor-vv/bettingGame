package com.example.bettingGame.core.converter;

import com.example.bettingGame.core.domain.custom.UserTourScoreBean;
import com.example.bettingGame.core.dto.TourScoreDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserTourScoreDtoConverter implements Converter<UserTourScoreBean, TourScoreDto> {

    @Override
    public TourScoreDto convert(UserTourScoreBean source) {
        return TourScoreDto.builder()
                .tourId(source.getTourId())
                .tourName(source.getTourName())
                .numberOfPoints(source.getNumberOfPoints())
                .build();
    }
}
