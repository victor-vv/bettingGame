package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Team;
import com.example.bettingGame.core.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeamsByName(String name) {
        return teamRepository.findAllByName(name);
    }
}
