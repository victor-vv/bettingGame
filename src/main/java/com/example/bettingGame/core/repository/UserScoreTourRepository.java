package com.example.bettingGame.core.repository;

import com.example.bettingGame.core.domain.UserScore;
import com.example.bettingGame.core.domain.UserScoreTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserScoreTourRepository extends JpaRepository<UserScoreTour, Long> {

}
