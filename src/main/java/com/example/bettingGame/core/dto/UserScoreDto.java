package com.example.bettingGame.core.dto;

import lombok.*;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserScoreDto {

    private Long userId;
    private String username;
    private Double numberOfPoints;
}
