package com.example.bettingGame.core.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class BetDto {

    @NotNull(message = "gameId can't be null")
    private Long gameId;
    @NotNull(message = "homeTeamScore can't be null")
    private Integer homeTeamScore;
    @NotNull(message = "awayTeamScore can't be null")
    private Integer awayTeamScore;
}
