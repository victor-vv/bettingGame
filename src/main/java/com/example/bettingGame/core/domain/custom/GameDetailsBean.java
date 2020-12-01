package com.example.bettingGame.core.domain.custom;

import lombok.*;

import java.util.Date;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class GameDetailsBean {

    private long gameId;
    private Date date;
    private String homeTeamName;
    private String awayTeamName;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private String tournamentName;
    private boolean finished;

}
