package com.cricket;

public class AddPlayer {
    public static void modifyPlayerTeam(int PlayerName,int playerId,String teamName)
    {
        int teamId = MatchUtils.getTeamId(teamName);
        int numbOfPlayer = MatchUtils.getNumberOfPlayer(teamName);
        if(numbOfPlayer==15) {
            System.out.println("player are not allow to get into this team");
            System.out.println("please provide another player name from team" +  teamName);

        }
        else {
            //MatchUtils.playerTeamUpdate(playerId,teamId);
        }
    }

    public static void addPlayerIntoTeam(String playerName, String teamName, int jerseyNumber)
    {
        boolean result = MatchUtils.checkForPlayer(playerName,teamName);
        int numbOfPlayer = MatchUtils.getNumberOfPlayer(teamName);
        if(result == true)
        {
            System.out.println("player already exist in the team");
            System.exit(1);
        }
        else
        {
            if(numbOfPlayer == 15) {
                System.out.println("Not place for new player");
                System.exit(1);
            }
            else
                MatchUtils.insertPlayerDetail(MatchUtils.getTeamId(teamName),playerName,jerseyNumber);
        }
    }
}
