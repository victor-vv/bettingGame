package com.example.bettingGame.core.repository;

import com.example.bettingGame.core.domain.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserScoreRepository extends UserScoreRepositoryCustom, JpaRepository<UserScore, Long> {

    Optional<UserScore> findByGameIdAndUserId(long gameId, long userId);
}
