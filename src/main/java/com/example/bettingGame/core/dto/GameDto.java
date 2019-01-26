package com.example.bettingGame.core.dto;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class GameDto {

    private long id;
    private Date date;
    private TeamDto homeTeam;
    private TeamDto awayTeam;
    private boolean finished;
    private String tournament;
}
