package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.domain.User;
import com.example.bettingGame.core.dto.GameDto;
import com.example.bettingGame.core.dto.GameResponseDto;
import com.example.bettingGame.core.dto.TourDto;
import com.example.bettingGame.core.service.GameService;
import com.example.bettingGame.core.service.TourService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tours")
public class TourController {

    private TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public List<TourDto> getTours(@RequestParam long tournamentId) {
        return tourService.getTours(tournamentId);
    }

}
