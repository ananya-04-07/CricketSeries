package com.cricket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MatchSeries {
    private int team1Id;
    private int team2Id;
    private String series_id;
    public void MatchStart(int matchNum, int numOfOver) {
        int tossWinningTeam = MatchUtils.toss(team1Id, team2Id);
        String batOrBall = MatchUtils.batOrBallDecision();
        int temp;
        if (MatchUtils.swapDecision(tossWinningTeam, team1Id, team2Id, batOrBall)) {
            temp = team1Id;
            team1Id = team2Id;
            team2Id = temp;
        }
        int row = MatchUtils.getDataToMatchDetail(series_id, matchNum, team1Id, team2Id, tossWinningTeam, batOrBall);
        System.out.println("---------   "+  row);
        MatchController matchController = new MatchController(team1Id, team2Id,numOfOver);
        matchController.playerSelection(MatchUtils.getPlayerList(team1Id, team2Id),series_id,matchNum);
    }


    public void SeriesStart() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dt = new Date();
        Scanner sc = new Scanner(System.in);
        System.out.println("Select SeriesId from the shown data");
        series_id = formatter.format(dt).toString();
        System.out.print("enter Series Name ----->  ");
        String seriesName = sc.nextLine();
        System.out.println();
        System.out.print("enter number of match in series ---->  ");
        int numbOfMatch = sc.nextInt();
        System.out.println();
        System.out.print("enter number of over ------>  ");
        int over = sc.nextInt();
        MatchUtils.getConnection();
        MatchUtils.getSeriesDetail(series_id,seriesName,numbOfMatch,over);
        System.out.println("select teams_id from team data");
        MatchUtils.showTeamTableData();
        System.out.print("enter team1Id  ----->   ");
        team1Id = sc.nextInt();
        System.out.print("enter team2Id ------>   ");
        team2Id = sc.nextInt();
        if (!MatchUtils.checkForValidTeamId(team1Id, team2Id) || team1Id == team2Id) {
            System.out.println("wrong teamId input");
            System.exit(1);
        }
        for (int i = 1; i <= numbOfMatch; i++) {
            System.out.println(i + "  " + "Match Starts");
            MatchStart(i,over);
            System.out.println("Match END");
            System.out.println("Score of Match");
        }


    }
}

