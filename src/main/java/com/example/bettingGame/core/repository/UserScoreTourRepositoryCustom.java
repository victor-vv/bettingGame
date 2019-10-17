package com.example.bettingGame.core.repository;

import com.example.bettingGame.core.domain.custom.UserScoreBean;
import com.example.bettingGame.core.domain.custom.UserScoreTourBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScoreTourRepositoryCustom {

    List<UserScoreTourBean> getUserScoresTourForTournament(long tournamentId);
}
