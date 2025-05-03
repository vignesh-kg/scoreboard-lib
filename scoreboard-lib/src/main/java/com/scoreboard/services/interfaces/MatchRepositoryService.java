package com.scoreboard.services.interfaces;

import com.scoreboard.models.Match;

import java.util.Collection;


public interface MatchRepositoryService {
    Boolean doesMatchExist(String homeTeam, String awayTeam);

    Match insertMatch(String homeTeam, String awayTeam);

    void removeMatch(String homeTeam, String awayTeam);

    Collection<Match> getMatchSummary();

    Match getMatch(String homeTeam, String awayTeam);
}
