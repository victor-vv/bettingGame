package com.example.bettingGame.core.service;

import com.example.bettingGame.core.domain.Bet;
import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.User;
import com.example.bettingGame.core.domain.UserScore;
import com.example.bettingGame.core.domain.custom.UserScoreBean;
import com.example.bettingGame.core.dto.UserRankingResponseDto;
import com.example.bettingGame.core.dto.UserScoreDto;
import com.example.bettingGame.core.repository.GameRepository;
import com.example.bettingGame.core.repository.UserRepository;
import com.example.bettingGame.core.repository.UserScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserScoreService {

    private UserScoreRepository userScoreRepository;
    private UserRepository userRepository;


    public UserScoreService(UserScoreRepository userScoreRepository, UserRepository userRepository) {
        this.userScoreRepository = userScoreRepository;
        this.userRepository = userRepository;
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

    @Transactional
    public UserRankingResponseDto getUserRankingForTournament(long tournamentId) {
        List<UserScoreBean> userScoresForTournament = userScoreRepository.getUserScoresForTournament(tournamentId);

        Map<Long, Integer> pointsForUser = userScoresForTournament.stream()
                .collect(Collectors.groupingBy(UserScoreBean::getUserId, Collectors.summingInt(UserScoreBean::getNumberOfPoints)));

        List<UserScoreDto> summedPoints = pointsForUser
                .keySet()
                .stream()
                .map(userId -> buildUserScoreDto(userId, pointsForUser.get(userId)))
                .sorted(Comparator.comparingInt(UserScoreDto::getNumberOfPoints))
                .collect(Collectors.toList());

        return new UserRankingResponseDto(tournamentId, summedPoints);
    }

    private UserScoreDto buildUserScoreDto(long userId, Integer numberOfPoints) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserScoreDto.builder()
                .userId(userId)
                .username(user.getUsername())
                .numberOfPoints(numberOfPoints)
                .build();
    }
}
