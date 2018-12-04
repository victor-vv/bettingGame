package com.example.bettingGame.core.domain;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "TEAMS")
public class Team {

    @Id
    @SequenceGenerator(name = "teams_sequence", sequenceName = "teams_seq")
    @GeneratedValue(generator = "teams_sequence")
    @Column(name = "TEAM_ID")
    private Long id;

    @Column(name = "TEAM_NAME", nullable = false)
    private String name;

    @Column(name = "TEAM_CITY", nullable = false)
    private String city;
}
