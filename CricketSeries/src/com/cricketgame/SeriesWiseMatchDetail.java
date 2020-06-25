package com.cricketgame;
import java.util.Map;

public class SeriesWiseMatchDetail {
    private Map<String,PlayerDetails> playersScore;
    private Map<String,TeamDetails> teamScore;

    public Map<String, PlayerDetails> getPlayersScore() {
        return playersScore;
    }

    public void setPlayersScore(Map<String, PlayerDetails> playersScore) {
        this.playersScore = playersScore;
    }

    public Map<String, TeamDetails> getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(Map<String, TeamDetails> teamScore) {
        this.teamScore = teamScore;
    }
}
