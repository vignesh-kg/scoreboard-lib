package com.scoreboard.util;

import com.scoreboard.constants.Constants;
import org.springframework.stereotype.Component;

@Component
public class ScoreBoardUtil {

    public String buildMatchKey(String homeTeam, String awayTeam){
        return homeTeam + Constants.MATCH_KEY_DELIMITTER + awayTeam;
    }
}