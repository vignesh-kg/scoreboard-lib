package com.scoreboard.services.interfaces;

import com.scoreboard.models.Match;
import com.scoreboard.models.Team;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ScoreBoardService {
    /**
     * startNewMatch() method is to start New match.
     * If a match is happening already between homeTeam and awayTeam, an exception will be thrown
     *
     * @param homeTeam
     * @param awayTeam
     * @return
     */
    Match startNewMatch(String homeTeam, String awayTeam);

    /**
     * Once a match is finished between a homeTeam and an awayTeam,
     * Score will be removed from the dashboard
     * @param homeTeam
     * @param awayTeam
     */
    void finishMatch(String homeTeam, String awayTeam);

    /**
     * Update score of teams
     * @param homeTeam
     * @param awayTeam
     * @return
     */
    Match updateScore(Team homeTeam, Team awayTeam);

    /**
     * List of matches which are currently happening will be returned
     * and is sorted by TotalScore of the match.
     * If multiple matches have same score, then it'll be sorted by insertion Order
     * @return
     */
    List<Match> getSummary();
}