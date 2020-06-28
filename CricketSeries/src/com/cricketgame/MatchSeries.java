package com.cricket;

import java.util.Scanner;

public class MatchSeries {
    private int team1Id;
    private int team2Id;
    private int series_id;
    public void MatchStart(int match_num)
    {
        int tossWinningTeam = MatchUtils.toss(team1Id,team2Id);
        String batOrBall = MatchUtils.batOrBallDecision();
        int temp;
        if(MatchUtils.swapDecision(tossWinningTeam,team1Id,team2Id,batOrBall))
        {
            temp = team1Id;
            team1Id = team2Id;
            team2Id = temp;
        }
        MatchUtils.getDataToMatchDetail(series_id,match_num,team1Id,team2Id,tossWinningTeam,batOrBall);
        MatchController matchController =  new MatchController(team1Id,team2Id, MatchUtils.getNumberOfOver(series_id));
        matchController.playerSelection(MatchUtils.getPlayerList(team1Id,team2Id),series_id,match_num,tossWinningTeam);
    }


    public void SeriesStart() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select SeriesId from the shown data");
        MatchUtils.getConnection();
        MatchUtils.showSeriesTableData();
        System.out.print("enter series id ----->  ");
        series_id = sc.nextInt();
        if(!MatchUtils.checkForValidSeriresId(series_id)) {
            System.out.println("wrong series id input");
            System.exit(1);
        }
        System.out.println("select teams_id from team data");
        MatchUtils.showTeamTableData();
        System.out.print("enter team1Id  ----->   ");
        team1Id = sc.nextInt();
        System.out.print("enter team2Id ------>   ");
        team2Id = sc.nextInt();
        if(!MatchUtils.checkForValidTeamId(team1Id,team2Id) || team1Id == team2Id)
        {
            System.out.println("wrong teamId input");
            System.exit(1);
        }
        int numbOfMatch = MatchUtils.getNumberOfMatch(series_id);
        for(int i = 1;i<=numbOfMatch;i++)
        {
            System.out.println(i + "  "+ "Match Starts");
            MatchStart(i);
            System.out.println("Match END");
            System.out.println("Score of Match");
            //seriesDetail.put(i,seriesWiseMatchDetail);
        }


    }
}
