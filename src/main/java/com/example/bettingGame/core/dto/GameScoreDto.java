package com.example.bettingGame.core.dto;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class GameScoreDto {

    private Long gameId;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
}
