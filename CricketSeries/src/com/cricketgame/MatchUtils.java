package com.cricket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatchUtils {
    private static Connection connection = null;
    private final static Random rand = new Random();

    public static int getRandomNumber(int range) {
        int t1 = rand.nextInt(range);
        return t1;
    }

    public static int binarySearch(int prefixArray[], int rand_number, int lowerIndex, int upperIndex) {
        int mid;
        while (lowerIndex < upperIndex) {
            mid = lowerIndex + ((upperIndex - lowerIndex) >> 1);
            if (rand_number > prefixArray[mid])
                lowerIndex = mid + 1;
            else
                upperIndex = mid;
        }
        return (prefixArray[lowerIndex] >= rand_number) ? lowerIndex : -1;
    }

    public static int getRun() {
        int runNo[] = {0, 1, 2, 3, 4, 5, 6, 7};
        int probabilityOfRun[] = {20, 20, 10, 5, 10, 5, 5, 5};
        int size = runNo.length;
        int prefix[] = new int[size], i;
        prefix[0] = probabilityOfRun[0];
        for (i = 1; i < size; ++i)
            prefix[i] = prefix[i - 1] + probabilityOfRun[i];
        int rand_num = ((int) (Math.random() * (323567)) % prefix[size - 1]) + 1;
        int index = binarySearch(prefix, rand_num, 0, size - 1);
        return runNo[index];
    }

    public static void getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/Cricket?", System.getenv("USER_NAME"), System.getenv("PASS_WORD"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void showSeriesTableData() {
        try (Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery("select * from series");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getInt(3));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void showTeamTableData() {
        try (Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery("select * from team");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean checkForValidSeriresId(int series_id) {
        boolean valid = false;
        try (Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery("select * from series");
            while (rs.next()) {
                if (rs.getInt(1) == series_id) {
                    valid = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }

    public static ResultSet getTeamResultSet() {
        ResultSet rs = null;
        try (Statement statement = connection.createStatement();) {
            rs = statement.executeQuery("select * from team");
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }

    public static boolean checkForValidTeamId(int team1Id, int team2Id) {
        boolean valid1 = false;
        boolean valid2 = false;
        try (Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery("select * from team");
            while (rs.next()) {
                if (rs.getInt(1) == team1Id) {
                    valid1 = true;
                }
                if (rs.getInt(1) == team2Id) {
                    valid2 = true;
                }
                if (valid1 && valid2)
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid1 && valid2;
    }

    public static String batOrBallDecision() {
        int rand_no = getRandomNumber(2);
        if (rand_no == 0)
            return Constants.BATTING;
        else
            return Constants.BALLING;
    }

    public static int toss(int team1, int team2) {
        int rand_no = getRandomNumber(2);
        if (rand_no == 0)
            return team1;
        else
            return team2;

    }

    public static boolean swapDecision(int tossWinningTeam, int team1Id, int team2Id, String batOrBall) {
        return (tossWinningTeam == team1Id && Constants.BALLING.equalsIgnoreCase(batOrBall) || tossWinningTeam == team2Id && Constants.BATTING.equalsIgnoreCase(batOrBall));
    }

    public static void getDataToMatchDetail(int series_id,int match_num, int team1Id, int team2Id,int tossWin,String batBallDecision) {
        String query = "insert into MatchDetail(Series_id,match_number,team1_id,team2_id,tossWIn,batBallDecision) values (?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1,series_id);
            preparedStatement.setInt(2,match_num);
            preparedStatement.setString(3,getTeamName(team1Id));
            preparedStatement.setString(4,getTeamName(team2Id));
            preparedStatement.setString(5,getTeamName(tossWin));
            preparedStatement.setString(6,batBallDecision);
            int result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int getNumberOfMatch(int Series_id) {
        String query = "select * from series where series_id = ? ";
        int numb_of_match = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, Series_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                numb_of_match = resultSet.getInt("numb_of_match");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return numb_of_match;
    }

    public static int getNumberOfOver(int Series_id) {
        String query = "select * from series where series_id = ? ";
        int over = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, Series_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                over = resultSet.getInt("over_num");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return over;
    }

    public static List<Integer> getPlayerList(int team1Id, int team2Id) {
        List<Integer> playerIdList = new ArrayList<>();
        String query = "select playerId from Player where team_id = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, team1Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                playerIdList.add(resultSet.getInt("playerId"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        query = "select playerId from Player where team_id = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, team2Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                playerIdList.add(resultSet.getInt("playerId"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return playerIdList;
    }

    public static String getTeamName(int teamId)
    {
        String query = "select * from Team where team_id = ? ";
        String teamName = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1, teamId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                teamName = resultSet.getString("team_name");

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return teamName;
    }

    public static void updateMatchDetail(int team1Score, int team2Score, String winningTeam) {
        String query = "update MatchDetail set team1Score = ? , team2Score = ? , WinningTeam = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setInt(1,team1Score);
            preparedStatement.setInt(2,team2Score);
            preparedStatement.setString(3,winningTeam);
            int result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


