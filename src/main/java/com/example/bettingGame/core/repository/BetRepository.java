package com.example.bettingGame.core.repository;

import com.example.bettingGame.core.domain.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BetRepository extends BetRepositoryCustom, JpaRepository<Bet, Long> {

    Optional<Bet> findByGameIdAndUserId(long gameId, long userId);

    List<Bet> findAllByGameId(long gameId);
}
