package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.dto.TourDto;
import com.example.bettingGame.core.service.GameService;
import com.example.bettingGame.core.service.TourService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tours")
public class TourController {

    private TourService tourService;
    private GameService gameService;

    public TourController(TourService tourService, GameService gameService) {
        this.tourService = tourService;
        this.gameService = gameService;
    }

    @GetMapping("/{tourId}")
    public TourDto getTourDetails(@PathVariable long tourId) {
        return tourService.getTourDetails(tourId);
    }

    @GetMapping
    public List<TourDto> getTours(@RequestParam long tournamentId) {
        return tourService.getTours(tournamentId);
    }

    @PutMapping(value = "/{tourId}/computed")
    public void closeGamesForTour(@PathVariable long tourId) {
        tourService.closeGamesForTour(tourId);
        tourService.closeTourForSecondSystem(tourId);
    }
}
