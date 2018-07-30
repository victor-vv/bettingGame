package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.domain.Team;
import com.example.bettingGame.core.service.TeamService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/team")
public class TeamController {

    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public List<Team> getInfo(@RequestParam String name) {
        return teamService.getAllTeamsByName(name);
    }
}
