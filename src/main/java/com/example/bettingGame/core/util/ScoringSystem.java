package com.example.bettingGame.core.util;

import java.util.HashMap;
import java.util.Map;

public enum ScoringSystem {
    TOTAL_POINTS(1, "Normal system, adds together points from one tour to another"),
    POINTS_BY_TOUR(2, "Every tour winner gets 1 points each tour, if there are more winners each gets 0,5 point");


    //TODO: move the calculating logic here
    private Integer id;
    private String description;

    private static Map<Integer, ScoringSystem> values;

    ScoringSystem(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    static {
        values = new HashMap<>();
        for (ScoringSystem scoringSystem : ScoringSystem.values()) {
            values.put(scoringSystem.getId(), scoringSystem);
        }
    }

    public Integer getId() {
        return id;
    }

}
