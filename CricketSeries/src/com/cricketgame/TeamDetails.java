package com.cricketgame;

public class TeamDetails {

    private String teamName;
    private String jerseyColor;
    private int runScore;
    private float runRate;
    private float overPlayed;
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public void setRunScore(int runScore) {
        this.runScore = runScore;
    }
    public void setRunRate(float runRate) {
        this.runRate = runRate;
    }
    public void setOverPlayed(float overPlayed) {
        this.overPlayed = overPlayed;
    }
    public int getRunScore() {
        return runScore;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getJerseyColor() {
        return jerseyColor;
    }

    public float getOverPlayed() {
        return overPlayed;
    }

    public float getRunRate() {
        return runRate;
    }
    public void setJerseyColor(String jerseyColor) {
        this.jerseyColor = jerseyColor;
    }
    public static class TeamDetailBuilder {
        private String teamName;
        private String jerseyColor;
        private int runScore;
        private float runRate;
        private float overPlayed;

        public TeamDetailBuilder(String teamName, String jerseyColor) {
            this.teamName = teamName;
            this.jerseyColor = jerseyColor;
        }
        public TeamDetailBuilder runScore(int value)
        {
            runScore = value;
            return this;
        }
        public TeamDetailBuilder runRate(float value)
        {
            runRate = value;
            return this;
        }
        public TeamDetailBuilder overPlayer(float value)
        {
            overPlayed = value;
            return this;
        }

        public TeamDetails team()
        {
            return new TeamDetails(this);
        }
    }
    public TeamDetails(TeamDetailBuilder teamDetailBuilder)
    {
        this.teamName = teamDetailBuilder.teamName;
        this.jerseyColor = teamDetailBuilder.jerseyColor;
        this.runScore = teamDetailBuilder.runScore;
        this.runRate = teamDetailBuilder.runRate;
        this.overPlayed = teamDetailBuilder.overPlayed;
    }

}
