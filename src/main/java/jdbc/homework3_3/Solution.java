package jdbc.homework3_3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Solution {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cfzzaolfgt6x.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "2210Dracon1983";

    private int count = 1000;

    public long testSavePerformance(){
        // Скорость выполнения 141592
        long delta = 0;
        try(Connection connection = getConnection()) {
            Date dtStart = new Date();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TEST_SPEED VALUES (?, ?, ?)");

            for (int i = 1; i <= count; i++){
                preparedStatement.setLong(1, i);
                preparedStatement.setString(2, "string - " + i);
                preparedStatement.setInt(3, i);

                preparedStatement.executeUpdate();
            }

            Date dtEnd = new Date();
            delta = dtEnd.getTime() - dtStart.getTime();
            return delta;
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return delta;
    }

    public long testDeleteByIdPerformance(){
        // Скорость выполнения 136861
        long delta = 0;
        try(Connection connection = getConnection()) {
            Date dtStart = new Date();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TEST_SPEED WHERE ID = ?");

            for (int i = 1; i <= count; i++){
                preparedStatement.setLong(1, i);

                preparedStatement.executeUpdate();
            }

            Date dtEnd = new Date();
            delta = dtEnd.getTime() - dtStart.getTime();
            return delta;
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return delta;
    }

    public long testDeletePerformance(){
        // Скорость выполнения 429
        long delta = 0;
        String sqlString = "DELETE FROM TEST_SPEED WHERE ID IN (";
        sqlString += addString(count);

        try(Connection connection = getConnection()) {
            Date dtStart = new Date();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);

            preparedStatement.executeUpdate();

            Date dtEnd = new Date();
            delta = dtEnd.getTime() - dtStart.getTime();
            return delta;
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return delta;
    }

    public long testSelectByIdPerformance(){
        // Скорость выполнения 136678
        long delta = 0;
        try(Connection connection = getConnection()) {
            Date dtStart = new Date();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TEST_SPEED WHERE ID = ?");

            for (int i = 1; i <= count; i++){
                preparedStatement.setLong(1, i);

                preparedStatement.executeQuery();
            }

            Date dtEnd = new Date();
            delta = dtEnd.getTime() - dtStart.getTime();
            return delta;
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return delta;
    }

    public long testSelectPerformance(){
        // Скорость выполнения 457
        long delta = 0;
        String sqlString = "SELECT * FROM TEST_SPEED WHERE ID IN (";
        sqlString += addString(count);

        try(Connection connection = getConnection()) {
            Date dtStart = new Date();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlString);

            preparedStatement.executeQuery();

            Date dtEnd = new Date();
            delta = dtEnd.getTime() - dtStart.getTime();
            return delta;
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return delta;
    }

    private String addString(int count){
        String sqlString = "";
        for (int i = 1; i <= count; i++){
            if(i == count){
                sqlString += i + ")";
                continue;
            }
            sqlString += i + ",";
        }
        return sqlString;
    }

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
