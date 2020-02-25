package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.*;
import com.example.bettingGame.core.domain.custom.UserScoreBean;
import com.example.bettingGame.core.domain.custom.UserScoreTourBean;
import com.example.bettingGame.core.dto.GameDto;
import com.example.bettingGame.core.dto.TourDto;
import com.example.bettingGame.core.dto.UserRankingResponseDto;
import com.example.bettingGame.core.dto.UserScoreDto;
import com.example.bettingGame.core.repository.UserRepository;
import com.example.bettingGame.core.repository.UserScoreRepository;
import com.example.bettingGame.core.repository.UserScoreTourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserScoreService {

    private final UserScoreRepository userScoreRepository;
    private final UserRepository userRepository;
    private final UserScoreTourRepository userScoreTourRepository;

    public UserScoreService(UserScoreRepository userScoreRepository, UserRepository userRepository, UserScoreTourRepository userScoreTourRepository) {
        this.userScoreRepository = userScoreRepository;
        this.userRepository = userRepository;
        this.userScoreTourRepository = userScoreTourRepository;
    }

    /**
     *
     * @param bet
     * @param game
     */
    @Transactional
    public void closeBet(Bet bet, Game game) {
        Integer homeTeamScore = game.getHomeTeamScore();
        Integer awayTeamScore = game.getAwayTeamScore();

        Integer homeTeamBet = bet.getHomeTeamScore();
        Integer awayTeamBet = bet.getAwayTeamScore();

        if (Arrays.asList(homeTeamScore, awayTeamScore, homeTeamBet, awayTeamBet).contains(null)) {
            log.warn("Bet <{}> for the game <{}> has some params missing", bet, game);
            throw new IllegalArgumentException("Can't close the bet, one of the params missing");
        }

        int pointsForGame = 0,
            betGoalsDif = homeTeamBet - awayTeamBet,
            finalScoreGoalsDif = homeTeamScore - awayTeamScore;
        if ((homeTeamBet.equals(homeTeamScore)) && (awayTeamBet.equals(awayTeamScore))) {
            pointsForGame = 5;
        } else if (betGoalsDif == finalScoreGoalsDif) {
            pointsForGame = 3;
        } else if ((!homeTeamScore.equals(awayTeamScore)) && (betGoalsDif > 0 && finalScoreGoalsDif > 0) || (betGoalsDif < 0 && finalScoreGoalsDif < 0)) {
            pointsForGame = 2;
        }
        Long userId = bet.getUserId();
        UserScore userScore = userScoreRepository.findByGameIdAndUserId(game.getId(), userId)
                .orElse(UserScore.builder()
                            .game(game)
                            .userId(userId)
                            .build());
        userScore.setNumberOfPoints(pointsForGame);
        userScoreRepository.save(userScore);
    }

    public void closeTour(TourDto tourDto, List<GameDto> games) {
        Long tourId = tourDto.getId();
        log.debug("Closing tour <{}>", tourId);
        Map<Long, Integer> pointsForUserId = games.stream()
                .flatMap(game -> userScoreRepository.findByGameId(game.getId()).stream())
                .collect(Collectors.groupingBy(UserScore::getUserId, Collectors.summingInt(UserScore::getNumberOfPoints)));
        if (pointsForUserId.isEmpty()) {
            log.debug("No bets for provided games are found");
            return;
        }
        int maxNumberOfPoints = pointsForUserId.values().stream().max(Comparator.naturalOrder()).get();
        List<Long> tourWinners = pointsForUserId.entrySet().stream()
                                    .filter(e -> e.getValue() == maxNumberOfPoints)
                                    .map(Map.Entry::getKey)
                                    .collect(Collectors.toList());
        Tour tour = Tour.builder()
                .id(tourId)
                .tournament(Tournament.builder().id(tourDto.getTournamentId()).build())
                .build();
        double numberOfPoints = (tourWinners.size() == 1) ? 1D : 0.5D;
        //TODO: this seems not to work
        userScoreTourRepository.findAllByTourId(tourId).forEach(userScoreTour -> userScoreTourRepository.deleteById(userScoreTour.getId()));

        tourWinners.forEach(userId -> {
            userScoreTourRepository.save(UserScoreTour.builder()
                    .tour(tour)
                    .numberOfPoints(numberOfPoints)
                    .userId(userId)
                    .build());
        });
    }

    @Transactional
    //TODO: убрать double и переделать в int (???)
    public UserRankingResponseDto getUserRankingForTournament(long tournamentId) {
        List<UserScoreBean> userScoresForTournament = userScoreRepository.getUserScoresForTournament(tournamentId);

        Map<Long, Double> pointsForUser = userScoresForTournament.stream()
                .collect(Collectors.groupingBy(UserScoreBean::getUserId, Collectors.summingDouble(UserScoreBean::getNumberOfPoints)));

        List<UserScoreDto> summedPoints = pointsForUser
                .keySet()
                .stream()
                .map(userId -> buildUserScoreDto(userId, pointsForUser.get(userId)))
                .sorted(Comparator.comparingDouble(UserScoreDto::getNumberOfPoints).reversed())
                .collect(Collectors.toList());

        return new UserRankingResponseDto(tournamentId, summedPoints);
    }


    @Transactional
    public UserRankingResponseDto getUserRankingTourForTournament(long tournamentId) {
        List<UserScoreTourBean> userScoresTourForTournament = userScoreTourRepository.getUserScoresTourForTournament(tournamentId);

        Map<Long, Double> pointsForUser = userScoresTourForTournament.stream()
                .collect(Collectors.groupingBy(UserScoreTourBean::getUserId, Collectors.summingDouble(UserScoreTourBean::getNumberOfPoints)));

        List<UserScoreDto> summedPoints = pointsForUser
                .keySet()
                .stream()
                .map(userId -> buildUserScoreDto(userId, pointsForUser.get(userId)))
                .sorted(Comparator.comparingDouble(UserScoreDto::getNumberOfPoints).reversed())
                .collect(Collectors.toList());

        return new UserRankingResponseDto(tournamentId, summedPoints);
    }
    private UserScoreDto buildUserScoreDto(long userId, Double numberOfPoints) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserScoreDto.builder()
                .userId(userId)
                .username(user.getUsername())
                .numberOfPoints(numberOfPoints)
                .build();
    }
}
