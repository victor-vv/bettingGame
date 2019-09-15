package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.dto.TourDto;
import com.example.bettingGame.core.service.TourService;
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

    @PutMapping(value = "/computed")
    public void computeTour(@RequestParam long tourId) {
        tourService.closeTour(tourId);
    }

}
