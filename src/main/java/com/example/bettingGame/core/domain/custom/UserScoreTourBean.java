package com.example.bettingGame.core.domain.custom;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserScoreTourBean {

    private long userId;
    private double numberOfPoints;
}
