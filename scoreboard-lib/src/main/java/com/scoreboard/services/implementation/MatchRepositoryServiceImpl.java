package com.scoreboard.services.implementation;

import com.scoreboard.models.Match;
import com.scoreboard.models.Team;
import com.scoreboard.repository.MatchRepository;
import com.scoreboard.services.interfaces.MatchRepositoryService;
import com.scoreboard.util.ModelFactory;
import com.scoreboard.util.ScoreBoardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MatchRepositoryServiceImpl implements MatchRepositoryService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ScoreBoardUtil scoreBoardUtil;

    @Autowired
    private ModelFactory modelFactory;

    @Override
    public Boolean doesMatchExist(String homeTeam, String awayTeam) {
        return matchRepository.getMatchRepository()
                .containsKey(scoreBoardUtil.buildMatchKey(homeTeam, awayTeam))
                ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Match insertMatch(String homeTeam, String awayTeam) {
        Match newMatch = modelFactory.match(new Team(homeTeam), new Team(awayTeam));
        newMatch.setMatchId(MatchRepository.getNewMatchId());
        matchRepository.getMatchRepository().put(scoreBoardUtil.buildMatchKey(homeTeam, awayTeam), newMatch);
        return newMatch;
    }

    @Override
    public void removeMatch(String homeTeam, String awayTeam) {
        matchRepository.getMatchRepository().remove(scoreBoardUtil.buildMatchKey(homeTeam, awayTeam));
    }

    @Override
    public Collection<Match> getMatchSummary() {
        return matchRepository.getMatchRepository().values();
    }

    @Override
    public Match getMatch(String homeTeam, String awayTeam) {
        return matchRepository.getMatchRepository().get(scoreBoardUtil.buildMatchKey(homeTeam, awayTeam));
    }
}