package com.scoreboard.services.test;

import com.scoreboard.exceptions.MatchDoesNotExistException;
import com.scoreboard.exceptions.MatchExistException;
import com.scoreboard.models.Match;
import com.scoreboard.models.Team;
import com.scoreboard.repository.MatchRepository;
import com.scoreboard.services.interfaces.ScoreBoardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import setup.BaseTest;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScoreBoardServiceTest extends BaseTest {
    @Autowired
    private ScoreBoardService scoreBoardService;

    @Autowired
    private MatchRepository matchRepository;

    @Test
    @Order(1)
    @DisplayName("Create New Match")
    @DirtiesContext
    public void test_createNewMatch_success() {
        Match newMatch = scoreBoardService.startNewMatch("Mexico", "Canada");
        Assertions.assertNotNull(newMatch);

        Assertions.assertAll("Matches",
                () -> Assertions.assertEquals("Mexico", newMatch.getHomeTeam().getTeamName()),
                () -> Assertions.assertEquals("Canada", newMatch.getAwayTeam().getTeamName()),
                () -> Assertions.assertEquals(0, newMatch.getHomeTeam().getScore()),
                () -> Assertions.assertEquals(0, newMatch.getAwayTeam().getScore())
        );
    }

    @Test
    @Order(3)
    @DisplayName("Throws MatchExistException as a match already exist for HomeTeam and AwayTeam")
    @DirtiesContext
    public void test_CreateNewMatch_throw_matchExist() {
        Assertions.assertThrows(MatchExistException.class, () -> {
            IntStream.range(0, 10).parallel().forEach(_ -> {
                scoreBoardService.startNewMatch("Mexico", "Canada");
            });
        });
    }

    @Test
    @Order(2)
    @DisplayName("To check no match having same Match Id")
    @DirtiesContext
    public void test_duplicate_matchId() {
        IntStream.range(0, 100).parallel().forEach(num -> {
            scoreBoardService.startNewMatch("Hteam" + num, "Ateam" + num);
        });
        boolean hasDuplicateMatchIds = matchRepository.getMatchRepository().values().stream()
                .collect(Collectors.groupingBy(Match::getMatchId, Collectors.counting())).values()
                .stream().anyMatch(count -> count > 1);
        Assertions.assertFalse(hasDuplicateMatchIds);
    }

    @Test
    @Order(4)
    @DisplayName("Finish a match from score board")
    @DirtiesContext
    public void test_finish_match_success() {
        IntStream.range(0, 100).parallel().forEach(num -> {
            scoreBoardService.startNewMatch("Hteam" + num, "Ateam" + num);
        });
        int initialScoreboardSize = matchRepository.getMatchRepository().size();
        scoreBoardService.finishMatch("Hteam10", "Ateam10");
        Assertions.assertEquals(initialScoreboardSize - 1, matchRepository.getMatchRepository().size());
    }

    @Test
    @Order(5)
    @DisplayName("Throws MatchDoesnotExistException as a match doesn't exist for HomeTeam and AwayTeam")
    @DirtiesContext
    public void test_FinishMatch_throw_match_not_Exist() {
        Assertions.assertThrows(MatchDoesNotExistException.class, () -> {
            IntStream.range(0, 10).parallel().forEach(_ -> {
                scoreBoardService.finishMatch("Mexico", "Canada");
            });
        });
    }

    @Test
    @Order(6)
    @DisplayName("Update score of Teams")
    @DirtiesContext
    public void test_Update_score_success() {
        Match match = scoreBoardService.startNewMatch("Mexico", "Canada");
        match.getHomeTeam().setScore(0);
        match.getAwayTeam().setScore(5);
        Match updatedMatch = scoreBoardService.updateScore(match.getHomeTeam(), match.getAwayTeam());
        Assertions.assertAll("Match scores",
                () -> Assertions.assertEquals(0, updatedMatch.getHomeTeam().getScore()),
                () -> Assertions.assertEquals(5, updatedMatch.getAwayTeam().getScore())
        );
    }

    @Test
    @Order(7)
    @DisplayName("Throws MatchDoesnotExistException as a match doesn't exist for HomeTeam and AwayTeam")
    @DirtiesContext
    public void test_UpdateScore_throw_match_not_Exist() {
        Assertions.assertThrows(MatchDoesNotExistException.class, () -> {
            IntStream.range(0, 10).parallel().forEach(_ -> {
                scoreBoardService.updateScore(new Team("Mexico"), new Team("Canada"));
            });
        });
    }
}