package com.example.bettingGame.core.repository.impl;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.custom.GameDetailsBean;
import com.example.bettingGame.core.repository.GameRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import static org.hibernate.criterion.Restrictions.*;

public class GameRepositoryImpl extends AbstractRepositoryImpl implements GameRepositoryCustom {

    @Override
    public GameDetailsBean getGameDetails(long gameId) {
        Criteria criteria = getSession().createCriteria(Game.class, "game")
                .createAlias("game.homeTeam", "homeTeam")
                .createAlias("game.awayTeam", "awayTeam")
                .createAlias("game.tournament", "tournament")
                .add(eq("game.id", gameId));

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("game.id"), "gameId")
                .add(Projections.property("game.date"), "date")
                .add(Projections.property("homeTeam.name"), "homeTeamName")
                .add(Projections.property("awayTeam.name"), "awayTeamName")
//                .add(Projections.property("game.homeTeamScore"), "homeTeamScore")
//                .add(Projections.property("game.awayTeamScore"), "awayTeamScore")
                .add(Projections.property("tournament.name"), "tournamentName"));

        criteria.setResultTransformer(Transformers.aliasToBean(GameDetailsBean.class));

        return (GameDetailsBean) criteria.uniqueResult();

    }

}
