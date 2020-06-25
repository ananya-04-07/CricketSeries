package com.cricketgame;

import java.util.Arrays;
import java.util.List;

public enum Team {
    THE_SON_OF_PITCHES(Arrays.asList("rahul_dravid","m_s_dhoni","virat_kohli","harbajan","sachine","dhawam","Rohit"), "Blue"),
    BLOODBATH_AND_BEYOND(Arrays.asList("Glenn_Maxwell","Mitchell_arsh","Ashton_Agar","Watson","Aron finch","Smith","XYZ"), "Yellow"),
    THE_MASTER_BATTER(Arrays.asList("Malinga","Karunathane","Parera","kusalParera","Lakmal","Mendis","Udana"),"Green");
    private final List<String> PlayerList;
    private final String jerserColor;
    Team(List<String> asList, String jerseyColor) {
        PlayerList = asList;
        this.jerserColor = jerseyColor;
    }

    public List<String> getPlayerList() {
        return PlayerList;
    }

    public String getJerserColor() {
        return jerserColor;
    }
}
