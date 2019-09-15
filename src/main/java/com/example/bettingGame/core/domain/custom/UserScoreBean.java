package com.example.bettingGame.core.domain.custom;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserScoreBean {

    private long userId;
    private Integer numberOfPoints;
}
