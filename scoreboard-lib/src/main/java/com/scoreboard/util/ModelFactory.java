package com.scoreboard.util;

import com.scoreboard.models.Match;
import com.scoreboard.models.Team;
import org.springframework.stereotype.Component;

@Component
public class ModelFactory {
    public Team team(String teamName){return new Team(teamName); }
    public Match match(Team homeTeam, Team awayTeam) { return new Match(homeTeam, awayTeam); }
}