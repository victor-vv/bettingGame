package com.example.bettingGame.core.repository.impl;

import com.example.bettingGame.core.domain.Game;
import com.example.bettingGame.core.domain.custom.GameDetailsBean;
import com.example.bettingGame.core.repository.GameRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hibernate.criterion.Restrictions.eq;

public class AbstractRepositoryImpl {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }
}
