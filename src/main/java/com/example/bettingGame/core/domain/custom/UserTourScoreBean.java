package com.example.bettingGame.core.domain.custom;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserTourScoreBean {

    private Long userId;
    private Long tourId;
    private String tourName;
    private Long numberOfPoints;
}
