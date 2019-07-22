package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.Tour;
import com.example.bettingGame.core.dto.TourDto;
import com.example.bettingGame.core.repository.GameRepository;
import com.example.bettingGame.core.repository.TourRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourService {

    private TourRepository tourRepository;
    private ConversionService conversionService;

    public TourService(TourRepository tourRepository, ConversionService conversionService) {
        this.tourRepository = tourRepository;
        this.conversionService = conversionService;
    }

    @Transactional
    public List<TourDto> getTours(long tournamentId) {
        List<Tour> tours = tourRepository.findAllByTournamentId(tournamentId);
        return tours.stream()
                .map(tour -> conversionService.convert(tour, TourDto.class))
                .collect(Collectors.toList());
    }
}