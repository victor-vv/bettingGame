package com.example.bettingGame.core.dto;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class TourDto {

    private Long id;
    private String name;
    private Date deadline;
    private Date dateFrom;
    private Date dateUntil;
    private Long tournamentId;
}
