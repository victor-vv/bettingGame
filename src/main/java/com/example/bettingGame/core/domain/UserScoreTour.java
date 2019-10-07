package com.example.bettingGame.core.domain;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "USER_SCORES_TOURS", schema = "BETTINGG")
public class UserScoreTour {

    @Id
    @SequenceGenerator(name = "USER_SCORES_TOURS_SEQUENCE", sequenceName = "BETTINGG.USER_SCORE_TOURS_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "USER_SCORES_TOURS_SEQUENCE")
    @Column(name = "USER_SCORE_TOURS_ID", insertable = false, updatable = false)
    private Long id;

    @Column(name = "USER_SCORE_TOURS_TOUR_ID", insertable = false, updatable = false)
    private Long tourId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_SCORE_TOURS_TOUR_ID")
    private Tour tour;

    @Column(name = "USER_SCORE_TOURS_POINTS")
    private Double numberOfPoints;

    @Column(name = "USER_SCORE_TOURS_USER_ID")
    private Long userId;
}