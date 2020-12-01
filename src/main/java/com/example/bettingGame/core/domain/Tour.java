package com.example.bettingGame.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "TOURS", schema = "BETTINGG")
public class Tour {

    @Id
    @SequenceGenerator(name = "TOURS_SEQUENCE", sequenceName = "BETTINGG.TOURS_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "TOURS_SEQUENCE")
    @Column(name = "TOUR_ID", insertable = false, updatable = false)
    private Long id;

    @Column(name = "TOUR_NAME")
    private String name;

    @Column(name = "TOUR_DEADLINE")
    private Date deadline;

    @Column(name = "TOUR_DATE_FROM")
    private Date dateFrom;

    @Column(name = "TOUR_DATE_UNTIL")
    private Date dateUntil;

    @Column(name = "TOUR_TOURNAMENT_ID", insertable = false, updatable = false)
    private Long tournamentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TOUR_TOURNAMENT_ID")
    private Tournament tournament;

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
    private Set<Game> games;
}