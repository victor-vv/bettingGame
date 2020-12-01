package com.example.bettingGame.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "USER_SCORES", schema = "BETTINGG")
public class UserScore {

    @Id
    @SequenceGenerator(name = "USER_SCORES_SEQUENCE", sequenceName = "BETTINGG.USER_SCORE_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "USER_SCORES_SEQUENCE")
    @Column(name = "USER_SCORE_ID", insertable = false, updatable = false)
    private Long id;

    @Column(name = "USER_SCORE_GAME_ID", insertable = false, updatable = false)
    private Long gameId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_SCORE_GAME_ID")
    private Game game;

    @Column(name = "USER_SCORE_POINTS")
    private Integer numberOfPoints;

    @Column(name = "USER_SCORE_USER_ID")
    private Long userId;
}