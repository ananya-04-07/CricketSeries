package com.cricketgame;

import java.util.HashMap;
import java.util.Map;

public class MatchSeries {
    private String team1;
    private String team2;
    private Map<Integer, SeriesWiseMatchDetail> seriesDetail = new HashMap<>();
    private SeriesWiseMatchDetail seriesWiseMatchDetail;

    public void seriesStart()
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
        matchController.insertTeamDetails(team1);
        matchController.insertTeamDetails(team2);
        seriesWiseMatchDetail = matchController.playerSelection(PlayerDetails.getPlayerList(team1,team2));
        System.out.println(seriesWiseMatchDetail);

    }
    public void displaySeriesDetail()
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
    public static void main(String[] args) {
        //---------Running Series of 4 matches-------------------------
        MatchSeries matchSeries = new MatchSeries();
        for(int i = 1;i<=4;i++)
        {
            System.out.println(i + "  "+ "Match Starts");
            matchSeries.seriesStart();
            System.out.println("Match END");
            System.out.println("Score of Match");
            matchSeries.seriesDetail.put(i,matchSeries.seriesWiseMatchDetail);
        }
        matchSeries.displaySeriesDetail();
    }
}

