package com.example.bettingGame.core.dto;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class GameDto {

    private Long id; //TODO: not needed, remove?
    private Date date;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long tour;
}
