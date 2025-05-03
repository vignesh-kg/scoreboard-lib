package com.scoreboard.repository;

import com.scoreboard.models.Match;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Scope("singleton")
public class MatchRepository {
    private static Long id = 0L;

    private final Map<String, Match> matchRepository;

    private MatchRepository() {
        this.matchRepository = new HashMap<>();
    }

    public Map<String, Match> getMatchRepository() {
        return matchRepository;
    }

    public static synchronized Long getNewMatchId() {
       return ++id;
    }
}