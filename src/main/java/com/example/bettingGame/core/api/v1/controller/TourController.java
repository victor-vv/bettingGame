package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.dto.TourDto;
import com.example.bettingGame.core.service.TourService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
