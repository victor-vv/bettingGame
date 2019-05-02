package com.example.bettingGame.core.domain;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "BETS")
public class Bet {

    @Id
    @SequenceGenerator(name = "bets_sequence", sequenceName = "bets_seq")
    @GeneratedValue(generator = "bets_sequence")
    @Column(name = "BET_ID")
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
    private Long user; //TODO: join User table
}
