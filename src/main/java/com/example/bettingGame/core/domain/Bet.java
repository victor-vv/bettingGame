package com.example.bettingGame.core.domain;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "BETS", schema = "BETTINGG")
public class Bet {

    @Id
    @SequenceGenerator(name = "BETS_SEQUENCE", sequenceName = "BETTINGG.BETS_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "BETS_SEQUENCE")
    @Column(name = "BET_ID", insertable = false, updatable = false)
    private Long id;

    @Column(name = "BET_GAME_ID", insertable = false, updatable = false)
    private Long gameId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BET_GAME_ID")
    private Game game;

    @Column(name = "BET_HOME_TEAM_SCORE", nullable = false)
    private Integer homeTeamScore;

    @Column(name = "BET_AWAY_TEAM_SCORE", nullable = false)
    private Integer awayTeamScore;

    @Column(name = "BET_USER_ID")
    private Long userId; //TODO: join User table
}