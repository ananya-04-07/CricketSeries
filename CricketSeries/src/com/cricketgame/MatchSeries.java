package com.cricketgame;

import java.util.HashMap;
import java.util.Map;

public class MatchSeries {
    private String team1;
    private String team2;
    private Map<Integer, SeriesWiseMatchDetail> seriesDetail = new HashMap<>();
    private SeriesWiseMatchDetail seriesWiseMatchDetail;

    public void MatchStart()
    {
        team1 = Team.values()[0].toString() ;
        team2 = Team.values()[1].toString();
        String tossWinningTeam = MatchUtils.toss(team1,team2);
        String batOrBall = MatchUtils.batOrBallDecision(tossWinningTeam);
        String temp = "";
        if(tossWinningTeam.equalsIgnoreCase(team1) && Constants.BALLING.equalsIgnoreCase(batOrBall) || (tossWinningTeam.equalsIgnoreCase(team2) && Constants.BATTING.equalsIgnoreCase(batOrBall)))
        {
            temp = team1;
            team1 = team2;
            team2 = temp;
        }
        MatchController matchController =  new MatchController(team1,team2,6);
        matchController.getTeamDetail(team1);
        matchController.getTeamDetail(team2);
        seriesWiseMatchDetail = matchController.playerSelection(PlayerDetails.getPlayerList(team1,team2));

    }
    public void displaySeriesResult()
    {
        for(SeriesWiseMatchDetail seriesWiseMatchDetail:seriesDetail.values())
        {
            System.out.println("in side");
            for (PlayerDetails playerDetails : seriesWiseMatchDetail.getPlayersScore().values())
            {
                System.out.println(playerDetails.getPlayerName() + "   " + playerDetails.getTeamName() + " " + playerDetails.getRun());
            }
            for(TeamDetails teamDetails : seriesWiseMatchDetail.getTeamScore().values())
            {
                System.out.println(teamDetails.getTeamName() + "  " + teamDetails.getJerseyColor() + "  " + teamDetails.getRunScore() + "  " + teamDetails.getRunRate() + "  " + teamDetails.getOverPlayed());
            }
        }
    }
    public void seriesStart()
    {
        for(int i = 1;i<=4;i++)
        {
            System.out.println(i + "  "+ "Match Starts");
            MatchStart();
            System.out.println("Match END");
            System.out.println("Score of Match");
            seriesDetail.put(i,seriesWiseMatchDetail);
        }
        displaySeriesResult();
    }

}

