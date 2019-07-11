package com.example.bettingGame.core.repository;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends GameRepositoryCustom, JpaRepository<Game, Long> {

    List<Game> findByTour(long tourId);

    List<Game> findByTournamentId(long tournamentId);
}
