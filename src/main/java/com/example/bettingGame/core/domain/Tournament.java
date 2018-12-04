package com.example.bettingGame.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "TOURNAMENTS")
public class Tournament {

    @Id
    @SequenceGenerator(name = "tournaments_sequence", sequenceName = "tournaments_seq")
    @GeneratedValue(generator = "tournaments_sequence")
    @Column(name = "TOURNAMENT_ID")
    private Long id;

    @Column(name = "TOURNAMENT_NAME")
    private String name;

    @Column(name = "TOURNAMENT_YEAR_FROM")
    private Integer yearFrom;

    @Column(name = "TOURNAMENT_YEAR_TO")
    private Integer yearTo;

    @OneToMany(mappedBy = "tournament", fetch = FetchType.LAZY)
    private Set<Game> games;
}
