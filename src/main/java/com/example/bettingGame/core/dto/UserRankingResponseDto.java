package com.example.bettingGame.core.dto;

import com.example.bettingGame.core.domain.custom.UserScoreBean;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserRankingResponseDto {

    private Long tournamentId;
    private List<UserScoreDto> userScores;
}
