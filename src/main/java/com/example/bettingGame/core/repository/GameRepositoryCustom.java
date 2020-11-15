package com.example.bettingGame.core.repository;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.custom.GameDetailsBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepositoryCustom {

    public GameDetailsBean getGameDetails(long gameId);

}
