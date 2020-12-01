package com.example.bettingGame.core.domain;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "TEAMS", schema = "BETTINGG")
public class Team {

    @Id
    @SequenceGenerator(name = "TEAMS_SEQUENCE", sequenceName = "BETTINGG.TEAMS_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "TEAMS_SEQUENCE")
    @Column(name = "TEAM_ID", insertable = false, updatable = false)
    private Long id;

    @Column(name = "TEAM_NAME", nullable = false)
    private String name;

    @Column(name = "TEAM_CITY", nullable = false)
    private String city;
}
