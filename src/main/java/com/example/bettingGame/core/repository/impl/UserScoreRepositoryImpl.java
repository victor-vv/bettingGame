package com.example.bettingGame.core.repository.impl;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.Tour;
import com.example.bettingGame.core.domain.Tournament;
import com.example.bettingGame.core.domain.UserScore;
import com.example.bettingGame.core.domain.custom.UserScoreBean;
import com.example.bettingGame.core.domain.custom.UserTourScoreBean;
import com.example.bettingGame.core.repository.UserScoreRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

public class UserScoreRepositoryImpl extends AbstractRepositoryImpl implements UserScoreRepositoryCustom {

    @Override
    public List<UserScoreBean> getUserScoresForTournament(long tournamentId) {

        Criteria criteria = getSession().createCriteria(UserScore.class, "userScore")
                .createAlias("userScore.game", "game")
                .createAlias("userScore.user", "user")
                .createAlias("game.tour", "tour")
                .createAlias("tour.tournament", "tournament")
                .add(eq("tournament.id", tournamentId))
                .add(eq("user.enabled", true));

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("userScore.userId"), "userId")
                .add(Projections.property("userScore.numberOfPoints"), "numberOfPoints"));

        criteria.setResultTransformer(Transformers.aliasToBean(UserScoreBean.class));

        return criteria.list();

    }

    @Override
    public List<UserTourScoreBean> getUserTourScoresForTournament(long tournamentId) {

//        Criteria criteria = getSession().createCriteria(UserScore.class, "userScore")
//                .createAlias("userScore.game", "game")
//                .createAlias("game.tour", "tour")
//                .createAlias("tour.tournament", "tournament")
//                .add(eq("tournament.id", tournamentId));
//
//        criteria.setProjection(Projections.projectionList()
//                .add(Projections.groupProperty()
//                )
//                .add(Projections.property("userScore.userId"), "userId")
//                .add(Projections.property("tour.id"), "tourId")
//                .add(Projections.property("tour.name"), "tourName")
//                .add(Projections.property("userScore.numberOfPoints"), "numberOfPoints"));
//
//        criteria.setResultTransformer(Transformers.aliasToBean(UserTourScoreBean.class));

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserTourScoreBean> criteriaQuery = builder.createQuery(UserTourScoreBean.class);
        Root<UserScore> userScore = criteriaQuery.from(UserScore.class);
        Join<UserScore, Game> game = userScore.join("game", JoinType.LEFT);
        Join<UserScore, Game> user = userScore.join("user", JoinType.LEFT);
        Join<Game, Tour> tour = game.join("tour", JoinType.LEFT);
        Join<Tour, Tournament> tournament = tour.join("tournament", JoinType.LEFT);

        criteriaQuery
                .multiselect(
                    userScore.get("userId"),
                    tour.get("id").alias("tourId"),
                    tour.get("name").alias("tourName"),
                    builder.sum(userScore.get("numberOfPoints")).alias("numberOfPoints")
                )
                .where(builder.and(
                        builder.equal(tournament.get("id"), tournamentId),
                        builder.equal(user.get("enabled"), true)
                       )
                )
                .groupBy(
                        userScore.get("userId"),
                        tour.get("id"),
                        tour.get("name")
                );
        return getSession().createQuery(criteriaQuery).getResultList();
    }

}
