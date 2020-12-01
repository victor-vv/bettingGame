package com.example.bettingGame.core.repository.impl;

import com.example.bettingGame.core.domain.UserScore;
import com.example.bettingGame.core.domain.UserScoreTour;
import com.example.bettingGame.core.domain.custom.UserScoreBean;
import com.example.bettingGame.core.domain.custom.UserScoreTourBean;
import com.example.bettingGame.core.repository.UserScoreRepositoryCustom;
import com.example.bettingGame.core.repository.UserScoreTourRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

public class UserScoreTourRepositoryImpl extends AbstractRepositoryImpl implements UserScoreTourRepositoryCustom {

    @Override
    public List<UserScoreTourBean> getUserScoresTourForTournament(long tournamentId) {

        Criteria criteria = getSession().createCriteria(UserScoreTour.class, "userScoreTour")
                .createAlias("userScoreTour.tour", "tour")
                .createAlias("tour.tournament", "tournament")
                .add(eq("tournament.id", tournamentId));

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("userScoreTour.userId"), "userId")
                .add(Projections.property("userScoreTour.numberOfPoints"), "numberOfPoints"));

        criteria.setResultTransformer(Transformers.aliasToBean(UserScoreTourBean.class));

        return criteria.list();

    }

}
