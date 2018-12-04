package com.example.bettingGame.core.domain;

import lombok.*;
import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "GAMES")
public class Game {

    @Id
    @SequenceGenerator(name = "games_sequence", sequenceName = "games_seq")
    @GeneratedValue(generator = "games_sequence")
    @Column(name = "GAME_ID")
    private Long id;

    @Column(name = "GAME_TOURNAMENT_ID", insertable = false, updatable = false)
    private Long tournamentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GAME_TOURNAMENT_ID")
    private Tournament tournament;

    @Column(name = "GAME_DATE")
    private Date date;

    @Column(name = "GAME_HOME_TEAM_ID", insertable = false, updatable = false)
    private Long homeTeamId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GAME_HOME_TEAM_ID")
    private Team homeTeam;

    @Column(name = "GAME_AWAY_TEAM_ID", insertable = false, updatable = false)
    private Long awayTeamId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GAME_AWAY_TEAM_ID")
    private Team awayTeam;

    @Column(name = "GAME_FINISHED")
    private short finished;

}
