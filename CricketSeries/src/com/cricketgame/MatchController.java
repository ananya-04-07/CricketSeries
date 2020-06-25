package com.cricketgame;

import java.util.HashMap;
import java.util.List;

public class MatchController {
    private String team1;
    private String team2;
    private int over;
    private PlayerDetails playerDetails;
    private int wicket;
    private String opener1;
    private String opener2;
    private String baller;
    private int ballerCount;
    private int ballPlayedByPlayer1;
    private int ballPlayedByPlayer2;
    private int ballPlayedTeam;
    private TeamDetails teamDetails;
    private HashMap<String, PlayerDetails> playersScore = new HashMap<>();
    private HashMap<String, TeamDetails> teamsScore = new HashMap<>();

    public MatchController(String team1, String team2, int over) {
        this.team1 = team1;
        this.team2 = team2;
        this.over = over;
    }

    public void changeBatsManOnWicket()
    {
        System.out.println("================================================");
        System.out.println("==============" + opener1 + " OUT! =========================");
        playerWicketUpdate(baller);
        updateBatsmanBall(opener1, ballPlayedByPlayer1);
        playerRunCount(opener1, 0, ballPlayedByPlayer1);
        System.out.println(getStrikeRate(opener1));
        wicket = wicket + 1;
        displayScoreOfPlayer(opener1, "");
        ballPlayedByPlayer1 = ballPlayedByPlayer2;
        ballPlayedByPlayer2 = 0;
        opener1 = opener2;
        System.out.println("================================================");
    }
    public void checkForOverEnd(List<String> team2Players)
    {
        int ballTemp;
        String temp;
        if (ballPlayedTeam % 6 == 0) {
            ballerCount = ballerCount + 1;
            baller = team2Players.get(ballerCount);
            temp = opener1;
            opener1 = opener2;
            opener2 = temp;
            ballTemp = ballPlayedByPlayer1;
            ballPlayedByPlayer1 = ballPlayedByPlayer2;
            ballPlayedByPlayer2 = ballTemp;
            System.out.println("=====" + ballPlayedTeam / 6 + " over completed ================");
            displayScoreOfPlayer(opener1, opener2);
        }
    }

    public void strikeChangeAndRunUpdate(int run)
    {
        String temp;
        int ballTemp;
        System.out.println("======" + opener1 + " hits " + run + " runs");
        playerRunCount(opener1, run, ballPlayedByPlayer1);
        ballTemp = ballPlayedByPlayer1;
        ballPlayedByPlayer1 = ballPlayedByPlayer2;
        ballPlayedByPlayer2 = ballTemp;
        temp = opener1;
        opener1 = opener2;
        opener2 = temp;
    }

    public void matchtStart(List<String> team1Players, List<String> team2Players) {
        opener1 = (team1Players.get(0));
        opener2 = team1Players.get(1);
        baller = (team2Players.get(0));
        ballerCount = 0;
        int totalBall = over * 6;
        wicket = 0;
        ballPlayedByPlayer1 = 0;
        ballPlayedByPlayer2 = 0;
        ballPlayedTeam = 0;
        for (ballPlayedTeam = 1; ballPlayedTeam <= totalBall; ballPlayedTeam++) {
            ballPlayedByPlayer1 = ballPlayedByPlayer1 + 1;
            System.out.println("========================================================");
            System.out.println("========= " + opener1 + " is on strike ===============");
            int run = MatchUtils.getRun();
            if (run == 7) {
                changeBatsManOnWicket();
                if (wicket == (team1Players.size()) - 1)
                    break;
                opener2 = team1Players.get(wicket + 1);
                System.out.println("============== " + opener2 + " =================");
            } else if (run == 0 || run == 2 || run == 4 || run == 6) {
                System.out.println("======" + opener1 + " hits " + run + " runs");
                playerRunCount(opener1, run, ballPlayedByPlayer1);
            } else if (run == 1 || run == 3 || run == 5) {
                strikeChangeAndRunUpdate(run);
            }
            checkForOverEnd(team2Players);
        }
    }

    public SeriesWiseMatchDetail playerSelection(List<String> players) {
        insertPlayerDetails(players);
        teamScoreBoardDisplay();
        List<String> team1Players = players.subList(0, players.size() / 2);
        List<String> team2Players = players.subList(players.size() / 2, players.size());
        System.out.println(team1Players);
        System.out.println(team2Players);
        System.out.println("============= FISRT INNING==================================");
        matchtStart(team1Players, team2Players);
        int totRunTeam1 = getTeamFinalScore(team1);
        teamRunUpdate(team1, totRunTeam1, ballPlayedTeam);
        System.out.println("============= SECOND INNING=================================");
        matchtStart(team2Players, team1Players);
        int totRunTeam2 = getTeamFinalScore(team2);
        teamRunUpdate(team2, totRunTeam2, ballPlayedTeam);
        teamScoreBoardDisplay();
        SeriesWiseMatchDetail seriesWiseMatchDetail = new SeriesWiseMatchDetail();
        seriesWiseMatchDetail.setPlayersScore(playersScore);
        seriesWiseMatchDetail.setTeamScore(teamsScore);
        return seriesWiseMatchDetail;
    }

    public void insertPlayerDetails(List<String> players) {
        PlayerDetails playerDetail;
        for (int i = 0; i < players.size(); i++) {
            if (i < players.size() / 2) {
                playerDetail = new PlayerDetails.PlayerBuilder(players.get(i), Constants.BATTING, MatchUtils.getJerseyColor(team1), team1, i).player();
            } else
                playerDetail = new PlayerDetails.PlayerBuilder(players.get(i), Constants.BALLING, MatchUtils.getJerseyColor(team2), team2, i).player();
            String playerId = MatchUtils.getUniquePlayerId(players.get(i), playerDetail.getJerseyNumber());
            playersScore.put(playerId, playerDetail);
        }
    }

    public void insertTeamDetails(String team) {
        TeamDetails teamDetail = new TeamDetails.TeamDetailBuilder(team, MatchUtils.getJerseyColor(team)).team();
        teamsScore.put(team, teamDetail);
    }

    public void playerRunCount(String playerName, int run, int ballFaced) {
        String playerId = MatchUtils.getPlayerNameFromId(playersScore, playerName);
        playerDetails = playersScore.get(playerId);
        System.out.println(playerDetails.getRun() + run);
        int runplay = playerDetails.getRun() + run;
        playerDetails.setRun(runplay);
        if (ballFaced != 0) {
            System.out.println(ballFaced);
            playerDetails.setStrikeRate((runplay * 100) / ballFaced);
        }
    }

    public void displayScoreOfPlayer(String player1, String player2) {

        String player1Id = MatchUtils.getPlayerNameFromId(playersScore, player1);
        if (player2.equals("")) {
            System.out.println("===========================================");
            System.out.print("=========Final Score of " + player1 + "======>  ");
            System.out.println(playersScore.get(player1Id).getRun());
        } else {
            String player2Id = MatchUtils.getPlayerNameFromId(playersScore, player2);
            System.out.println("===========================================");
            System.out.print("========= Score of " + player1 + " after over ======>  ");
            System.out.println(playersScore.get(player1Id).getRun()
            );
            System.out.print("========= Score of " + player2 + " after over ======>  ");
            System.out.println(playersScore.get(player2Id).getRun());
        }
    }

    public int getTeamFinalScore(String team) {
        int sum = 0;
        for (PlayerDetails player : playersScore.values()) {
            if (player.getTeamName().equalsIgnoreCase(team))
                sum = sum + player.getRun();
        }
        return sum;
    }

    public void playerWicketUpdate(String player) {
        String playerId = MatchUtils.getPlayerNameFromId(playersScore, player);
        playerDetails = playersScore.get(playerId);
        int wicket = playerDetails.getWicket();
        playerDetails.setWicket(wicket + 1);

    }

    public void updateBatsmanBall(String playerName, int ballPlayedByPlayer1) {
        String playerId = MatchUtils.getPlayerNameFromId(playersScore, playerName);
        playerDetails = playersScore.get(playerId);
        float overPlay = playerDetails.getOverPlayed();
        float totalOver = (float) ballPlayedByPlayer1 / 6 + overPlay;
        playerDetails.setOverPlayed(totalOver);
    }

    public float getStrikeRate(String playerName) {
        String playerId = MatchUtils.getPlayerNameFromId(playersScore, playerName);
        PlayerDetails pd = playersScore.get(playerId);
        return pd.getStrikeRate();
    }

    public int getPlayerRun(String playerName) {
        String playerId = MatchUtils.getPlayerNameFromId(playersScore, playerName);
        PlayerDetails pd = playersScore.get(playerId);
        return pd.getRun();
    }

    public void teamRunUpdate(String team, int run, int ball) {
        System.out.println(team);
        teamDetails = teamsScore.get(team);
        int runprev = teamDetails.getRunScore();
        float overPlayed = (float) ball / 6;
        float runRate = (float) (run + runprev) / overPlayed;
        teamDetails.setRunScore(run);
        teamDetails.setOverPlayed(overPlayed);
        teamDetails.setRunRate(runRate);

    }

    public float getRunRate(String team) {
        teamDetails = teamsScore.get(team);
        return teamDetails.getRunRate();
    }

    public int getTeamScore(String team) {
        teamDetails = teamsScore.get(team);
        return teamDetails.getRunScore();
    }

    public void teamScoreBoardDisplay() {
        for (TeamDetails tm : teamsScore.values()) {
            System.out.println(tm.getTeamName() + "  " + tm.getJerseyColor() + "  " + tm.getRunScore() + "  " + tm.getRunRate() + "  " + tm.getOverPlayed());
        }
    }

}

