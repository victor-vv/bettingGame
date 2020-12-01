package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Tour;
import com.example.bettingGame.core.dto.TourDto;
import com.example.bettingGame.core.repository.TourRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourService {

    private TourRepository tourRepository;
    private ConversionService conversionService;
    private GameService gameService;

    public TourService(TourRepository tourRepository, ConversionService conversionService, GameService gameService) {
        this.tourRepository = tourRepository;
        this.conversionService = conversionService;
        this.gameService = gameService;
    }


    public TourDto getTourDetails(long tourId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new EntityNotFoundException("Tour not found"));
        return conversionService.convert(tour, TourDto.class);
    }

    public List<TourDto> getTours(long tournamentId) {
        List<Tour> tours = tourRepository.findAllByTournamentId(tournamentId);
        return tours.stream()
                .map(tour -> conversionService.convert(tour, TourDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void closeGamesForTour(long tourId) {
        gameService.closeGamesForTour(tourId);
    }

    @Transactional
    public void closeTourForSecondSystem (long tourId) {
        TourDto tourDto = getTourDetails(tourId);
        gameService.computeTourForSecondSystem(tourDto);
    }
}