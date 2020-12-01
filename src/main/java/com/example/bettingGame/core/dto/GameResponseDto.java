package com.example.bettingGame.core.dto;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class GameResponseDto {

    private Long id;
    private Date date;
    private TeamDto homeTeam;
    private TeamDto awayTeam;
    private boolean finished;
    private TournamentDto tournament;
    private Long tourNumber;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private Integer homeTeamBet;
    private Integer awayTeamBet;
    private long userId;
}
