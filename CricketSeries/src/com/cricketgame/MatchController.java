package com.cricket;

import java.util.List;

public class MatchController {
    private int team1Id;
    private int team2Id;
    private int over;
    private int wicket;
    private int opener1;
    private int opener2;
    private int baller;
    private int ballerCount;
    private int ballPlayedByPlayer1;
    private int ballPlayedByPlayer2;
    private int ballPlayedTeam;
    public MatchController(int team1Id,int team2Id,int over)
    {
        this.over = over;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
    }
    public void changeBatsManOnWicket()
    {
        System.out.println("================================================");
        System.out.println("==============" + opener1 + " OUT! =========================");
        wicket = wicket + 1;
        ballPlayedByPlayer1 = ballPlayedByPlayer2;
        ballPlayedByPlayer2 = 0;
        opener1 = opener2;
        System.out.println("================================================");
    }
    public void checkForOverEnd(List<Integer> team2Players)
    {
        int ballTemp;
        int temp;
        if (ballPlayedTeam % 6 == 0) {
            ballerCount = ballerCount + 1;
            if(ballerCount>=team2Players.size())
                ballerCount = 0;
            baller = team2Players.get(ballerCount);
            temp = opener1;
            opener1 = opener2;
            opener2 = temp;
            ballTemp = ballPlayedByPlayer1;
            ballPlayedByPlayer1 = ballPlayedByPlayer2;
            ballPlayedByPlayer2 = ballTemp;
            System.out.println("=====" + ballPlayedTeam / 6 + " over completed ================");
        }
    }

    public void strikeChangeAndRunUpdate(int run)
    {
        int temp;
        int ballTemp;
        System.out.println("======" + opener1 + " hits " + run + " runs");
        ballTemp = ballPlayedByPlayer1;
        ballPlayedByPlayer1 = ballPlayedByPlayer2;
        ballPlayedByPlayer2 = ballTemp;
        temp = opener1;
        opener1 = opener2;
        opener2 = temp;
    }

    public int InningsStart(List<Integer> team1Players, List<Integer> team2Players,int inningNumber,String seriesId,int matchId) {
        opener1 = (team1Players.get(0));
        opener2 = team1Players.get(1);
        baller = (team2Players.get(0));
        ballerCount = 0;
        int totalBall = over * 6;
        wicket = 0;
        ballPlayedByPlayer1 = 0;
        ballPlayedByPlayer2 = 0;
        ballPlayedTeam = 0;
        int totalRun = 0;
        for (ballPlayedTeam = 1; ballPlayedTeam <= totalBall; ballPlayedTeam++) {
            System.out.println("========================================================");
            System.out.println("========= " + MatchUtils.getPlayerName(opener1) + " is on strike ===============");
            int run = MatchUtils.getRun();
            MatchUtils.getScoreBoard(matchId,inningNumber,ballPlayedTeam,MatchUtils.getPlayerName(opener1),seriesId,run);
            totalRun = totalRun + run;
            ballPlayedByPlayer1 = ballPlayedByPlayer1 + 1;
            if (run == 7) {
                changeBatsManOnWicket();
                if (wicket == (team1Players.size()))
                    break;
                opener2 = team1Players.get(wicket + 1);
                System.out.println("============== " + opener2 + " =================");
            } else if (run == 0 || run == 2 || run == 4 || run == 6) {
                System.out.println("======" + MatchUtils.getPlayerName(opener1) + " hits " + run + " runs");
            } else if (run == 1 || run == 3 || run == 5) {
                strikeChangeAndRunUpdate(run);
            }
            checkForOverEnd(team2Players);
        }
        return totalRun;
    }

    public void playerSelection(List<Integer> players,String seriesId,int matchId) {
        List<Integer> team1Players = players.subList(0, players.size() / 2);
        List<Integer> team2Players = players.subList(players.size() / 2, players.size());
        System.out.println(team1Players + "   " + team1Players.size());
        System.out.println(team2Players + "   " + team2Players.size());
        System.out.println("============= FISRT INNING==================================");
        int totalRunteam1 = InningsStart(team1Players, team2Players,1,seriesId,matchId);
        System.out.println(totalRunteam1);
        System.out.println("============= SECOND INNING=================================");
        int totalRunteam2 = InningsStart(team2Players, team1Players,2,seriesId,matchId);
        String matchWinningTeam = MatchUtils.getTeamName(team1Id);
        System.out.println(totalRunteam2);
        if(totalRunteam2>totalRunteam1)
            matchWinningTeam = MatchUtils.getTeamName(team2Id);
        MatchUtils.updateMatchDetail(totalRunteam1,totalRunteam2,matchWinningTeam,seriesId,matchId);

    }


}
