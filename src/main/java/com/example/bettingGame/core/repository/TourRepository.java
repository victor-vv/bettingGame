package com.example.bettingGame.core.repository;

import com.example.bettingGame.core.domain.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findAllByTournamentId(long tournamentId);
}
