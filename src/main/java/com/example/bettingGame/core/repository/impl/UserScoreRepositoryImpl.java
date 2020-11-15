package com.example.bettingGame.core.repository.impl;

import com.example.bettingGame.core.domain.UserScore;
import com.example.bettingGame.core.domain.custom.UserScoreBean;
import com.example.bettingGame.core.repository.UserScoreRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

public class UserScoreRepositoryImpl extends AbstractRepositoryImpl implements UserScoreRepositoryCustom {

    @Override
    public List<UserScoreBean> getUserScoresForTournament(long tournamentId) {

        Criteria criteria = getSession().createCriteria(UserScore.class, "userScore")
                .createAlias("userScore.game", "game")
                .createAlias("game.tour", "tour")
                .createAlias("tour.tournament", "tournament")
                .add(eq("tournament.id", tournamentId));

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("userScore.userId"), "userId")
                .add(Projections.property("userScore.numberOfPoints"), "numberOfPoints"));

        criteria.setResultTransformer(Transformers.aliasToBean(UserScoreBean.class));

        return criteria.list();

    }

}
