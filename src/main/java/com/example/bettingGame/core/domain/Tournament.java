package com.example.bettingGame.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "TOURNAMENTS", schema = "BETTINGG")
public class Tournament {

    @Id
    @SequenceGenerator(name = "TOURNAMENTS_SEQUENCE", sequenceName = "BETTINGG.GAMES_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "TOURNAMENTS_SEQUENCE")
    @Column(name = "TOURNAMENT_ID", insertable = false, updatable = false)
    private Long id;

    @Column(name = "TOURNAMENT_NAME")
    private String name;

    @Column(name = "TOURNAMENT_YEAR_FROM")
    private Integer yearFrom;

    @Column(name = "TOURNAMENT_YEAR_TO")
    private Integer yearTo;

    @OneToMany(mappedBy = "tournament", fetch = FetchType.LAZY)
    private Set<Game> games;

    //TODO: убрать года и поставить флаг
}
