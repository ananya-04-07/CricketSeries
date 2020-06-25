package com.cricketgame;

import java.util.ArrayList;
import java.util.List;

public class PlayerDetails {
    private int run;
    private String playerName;
    private float overPlayed;
    private float strikeRate;
    private int wicket;
    private int ballBowled;
    private String playerType;
    private String teamName;
    private int jerseyNumber;
    private String jerseyColor;
    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public String getJerseyColor() {
        return jerseyColor;
    }
    public void setRun(int run) {
        this.run = run;
    }
    public void setOverPlayed(float overPlayed) {
        this.overPlayed = overPlayed;
    }
    public void setStrikeRate(float strikeRate) {
        this.strikeRate = strikeRate;
    }
    public void setWicket(int wicketTaken) {
        this.wicket = wicketTaken;
    }
    public int getRun() {
        return run;
    }
    public float getOverPlayed() {
        return overPlayed;
    }
    public float getStrikeRate() {
        return strikeRate;
    }
    public int getWicket() {
        return wicket;
    }
    public int getBallBowled() {
        return ballBowled;
    }
    public String getPlayerType() {
        return playerType;
    }
    public String getTeamName() {
        return teamName;
    }
    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public static List<String> getPlayerList(String team1, String team2) {
        List<String> playerName = new ArrayList<>();
        for (Team c : Team.values()) {
            if (c.toString().equalsIgnoreCase(team1)) {
                playerName.addAll(c.getPlayerList());
            }
        }
        for (Team c : Team.values()) {
            if (c.toString().equalsIgnoreCase(team2)) {
                playerName.addAll(c.getPlayerList());
            }
        }
        return playerName;
    }

    public static class PlayerBuilder
    {
        private String playerName;
        private String playerType;
        private String jerseyColor;
        private int run;
        private float overPlayed;
        private float strikeRate;
        private int wicket;
        private int ballBowled;
        private String teamName;
        private int jerseyNumber;

        public PlayerBuilder(String playerName, String playerType, String jerseyColor, String teamName, int jerseyNumber)
        {
            this.playerName = playerName;
            this.playerType = playerType;
            this.jerseyColor= jerseyColor;
            this.teamName = teamName;
            this.jerseyNumber = jerseyNumber;
        }

        public PlayerBuilder run(int val)
        { run = val;      return this; }
        public PlayerBuilder overPlayed(float val)
        { overPlayed = val;           return this; }
        public PlayerBuilder strikeRate(float val)
        { strikeRate = val;        return this; }
        public PlayerBuilder wicket(int val)
        { wicket = val;  return this; }
        public PlayerBuilder ballBowled(int val)
        { ballBowled = val;      return this; }
        public PlayerBuilder overPlayed(int val)
        { overPlayed = val;           return this; }

        public PlayerDetails player()
        {
            return new PlayerDetails(this);
        }

    }
    private PlayerDetails(PlayerBuilder playerbuilder)
    {
        this.playerName = playerbuilder.playerName;
        this.playerType = playerbuilder.playerType;
        this.jerseyColor = playerbuilder.jerseyColor;
        this.run  = playerbuilder.run;
        this.overPlayed = playerbuilder.overPlayed;
        this.strikeRate = playerbuilder.strikeRate;
        this.wicket = playerbuilder.wicket;
        this.ballBowled = playerbuilder.ballBowled;
        this.teamName = playerbuilder.teamName;
        this.jerseyNumber = playerbuilder.jerseyNumber;
    }



}
