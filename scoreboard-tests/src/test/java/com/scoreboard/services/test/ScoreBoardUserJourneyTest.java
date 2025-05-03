package com.scoreboard.services.test;

import com.scoreboard.models.Match;
import com.scoreboard.services.interfaces.ScoreBoardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import setup.BaseTest;

import java.util.Arrays;
import java.util.List;

public class ScoreBoardUserJourneyTest extends BaseTest {
    @Autowired
    private ScoreBoardService scoreBoardService;

    @Test
    @DisplayName("Lifecycle of match")
    public void test_match_life_cycle() {
        Match matchA = scoreBoardService.startNewMatch("Mexico", "Canada");
        Match matchB = scoreBoardService.startNewMatch("Spain", "Brazil");
        Match matchC = scoreBoardService.startNewMatch("Germany", "France");
        Match matchD = scoreBoardService.startNewMatch("Uruguay", "Italy");
        Match matchE = scoreBoardService.startNewMatch("Argentina", "Australia");

        matchA.getHomeTeam().setScore(0);
        matchA.getAwayTeam().setScore(5);
        matchB.getHomeTeam().setScore(10);
        matchB.getAwayTeam().setScore(2);
        matchC.getHomeTeam().setScore(2);
        matchC.getAwayTeam().setScore(2);
        matchD.getHomeTeam().setScore(6);
        matchD.getAwayTeam().setScore(6);
        matchE.getHomeTeam().setScore(3);
        matchE.getAwayTeam().setScore(1);

        scoreBoardService.updateScore(matchA.getHomeTeam(), matchA.getAwayTeam());
        scoreBoardService.updateScore(matchB.getHomeTeam(), matchB.getAwayTeam());
        scoreBoardService.updateScore(matchC.getHomeTeam(), matchC.getAwayTeam());
        scoreBoardService.updateScore(matchD.getHomeTeam(), matchD.getAwayTeam());
        scoreBoardService.updateScore(matchE.getHomeTeam(), matchE.getAwayTeam());

        List<String> matchSummary = scoreBoardService.getSummary().stream()
                .map(entry -> String.format("%s %d | %s %d",
                        entry.getHomeTeam().getTeamName(),
                        entry.getHomeTeam().getScore(),
                        entry.getAwayTeam().getTeamName(),
                        entry.getAwayTeam().getScore()))
                .toList();

        List<String> expectedMatchSummary = Arrays.asList(new String[]{"Uruguay 6 | Italy 6", "Spain 10 | Brazil 2", "Mexico 0 | Canada 5", "Argentina 3 | Australia 1", "Germany 2 | France 2"});
        Assertions.assertEquals(expectedMatchSummary, matchSummary);

        scoreBoardService.finishMatch("Argentina", "Australia");

        matchSummary = scoreBoardService.getSummary().stream()
                .map(entry -> String.format("%s %d | %s %d",
                        entry.getHomeTeam().getTeamName(),
                        entry.getHomeTeam().getScore(),
                        entry.getAwayTeam().getTeamName(),
                        entry.getAwayTeam().getScore()))
                .toList();

        expectedMatchSummary = Arrays.asList(new String[]{"Uruguay 6 | Italy 6", "Spain 10 | Brazil 2", "Mexico 0 | Canada 5", "Germany 2 | France 2"});
        Assertions.assertEquals(expectedMatchSummary, matchSummary);
    }
}
