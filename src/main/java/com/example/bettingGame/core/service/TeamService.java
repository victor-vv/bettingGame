package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Team;
import com.example.bettingGame.core.domain.custom.GameDetailsBean;
import com.example.bettingGame.core.repository.GameRepository;
import com.example.bettingGame.core.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;
    private GameRepository gameRepository;

    public TeamService(TeamRepository teamRepository, GameRepository gameRepository) {
        this.teamRepository = teamRepository;
        this.gameRepository = gameRepository;
    }

    public List<Team> getTeamByName(String name) {
        GameDetailsBean result = gameRepository.getGameDetails(1L);
        if (StringUtils.isEmpty(name)) {
            return teamRepository.findAll();
        }
        return teamRepository.findAllByName(name);
    }
}
