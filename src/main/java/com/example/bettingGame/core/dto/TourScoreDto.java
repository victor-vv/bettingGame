package com.example.bettingGame.core.dto;

import lombok.*;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class TourScoreDto {

    private Long tourId;
    private String tourName;
    private Long numberOfPoints;
}
