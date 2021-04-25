package com.example.bettingGame.core.repository;

import com.example.bettingGame.core.domain.custom.UserScoreBean;
import com.example.bettingGame.core.domain.custom.UserTourScoreBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScoreRepositoryCustom {

    List<UserScoreBean> getUserScoresForTournament(long tournamentId);

    List<UserTourScoreBean> getUserTourScoresForTournament(long tournamentId);
}
