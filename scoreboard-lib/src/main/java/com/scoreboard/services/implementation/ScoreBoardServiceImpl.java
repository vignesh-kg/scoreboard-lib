package com.scoreboard.services.implementation;

import com.scoreboard.constants.Constants;
import com.scoreboard.exceptions.InvalidInputException;
import com.scoreboard.exceptions.MatchDoesNotExistException;
import com.scoreboard.exceptions.MatchExistException;
import com.scoreboard.models.Match;
import com.scoreboard.models.Team;
import com.scoreboard.services.interfaces.MatchRepositoryService;
import com.scoreboard.services.interfaces.ScoreBoardService;
import com.scoreboard.util.ModelFactory;
import com.scoreboard.util.ScoreBoardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.BiConsumer;

@Service
public class ScoreBoardServiceImpl implements ScoreBoardService {
    @Autowired
    private MatchRepositoryService matchRepositoryService;

    @Autowired
    private ModelFactory modelFactory;

    @Autowired
    private ScoreBoardUtil scoreBoardUtil;

    private final BiConsumer<String, String> validateIfMatchExist = (homeTeam, awayTeam) -> {
        if (!matchRepositoryService.doesMatchExist(homeTeam, awayTeam)) {
            throw new MatchDoesNotExistException(String.format(Constants.MATCH_DOESNOT_EXIST, homeTeam, awayTeam));
        }
    };

    private final BiConsumer<String, String> validateIfEmptyTeams = (homeTeam, awayTeam) -> {
        if (!(StringUtils.hasText(homeTeam) || StringUtils.hasText(awayTeam)))
            throw new InvalidInputException("Teams cannot be empty");
        if (!StringUtils.hasText(homeTeam))
            throw new InvalidInputException(String.format(Constants.EMPTY_TEAM, Constants.HOME_TEAM));
        if (!StringUtils.hasText(awayTeam))
            throw new InvalidInputException(String.format(Constants.EMPTY_TEAM, Constants.AWAY_TEAM));
    };

    @Override
    public Match startNewMatch(String homeTeam, String awayTeam) {
        validateIfEmptyTeams.accept(homeTeam, awayTeam);
        //Validate If match exist and If there is already a match between homeTeam and AwayTeam throw exception
        BiConsumer<String, String> validateIfMatchDoesntExist = (homeTeamName, awayTeamName) -> {
            if (matchRepositoryService.doesMatchExist(homeTeamName, awayTeamName)) {
                throw new MatchExistException(String.format(Constants.MATCH_ALREADY_EXIST, homeTeamName, awayTeamName));
            }
        };
        validateIfMatchDoesntExist.accept(homeTeam, awayTeam);
        return matchRepositoryService.insertMatch(homeTeam, awayTeam);
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        validateIfEmptyTeams.accept(homeTeam, awayTeam);
        validateIfMatchExist.accept(homeTeam, awayTeam);
        matchRepositoryService.removeMatch(homeTeam, awayTeam);
    }

    @Override
    public Match updateScore(Team homeTeam, Team awayTeam) {
        BiConsumer<Team, Team> validateIfTeamsEmpty = (hTeam, aTeam) -> {
            if (null == hTeam && null == aTeam)
                throw new InvalidInputException("Teams cannot be empty");
            if (null == hTeam)
                throw new InvalidInputException(String.format(Constants.EMPTY_TEAM, Constants.HOME_TEAM));
            if (null == aTeam)
                throw new InvalidInputException(String.format(Constants.EMPTY_TEAM, Constants.AWAY_TEAM));
        };

        validateIfTeamsEmpty.accept(homeTeam, awayTeam);
        validateIfMatchExist.accept(homeTeam.getTeamName(), awayTeam.getTeamName());
        return matchRepositoryService.getMatch(homeTeam.getTeamName(), awayTeam.getTeamName());
    }

    @Override
    public List<Match> getSummary() {
        return matchRepositoryService.getMatchSummary().stream().sorted((matchA, matchB) -> {
            int scoreComparision = Integer.compare(((Match) matchB).getHomeTeam().getScore() + ((Match) matchB).getAwayTeam().getScore(),
                    ((Match) matchA).getHomeTeam().getScore() + ((Match) matchA).getAwayTeam().getScore());
            if (scoreComparision == 0)
                return Long.compare(((Match) matchB).getMatchId(), ((Match) matchA).getMatchId());
            return scoreComparision;
        }).toList();
    }
}