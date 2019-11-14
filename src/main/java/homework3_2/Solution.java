package homework3_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Solution {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cfzzaolfgt6x.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "2210Dracon1983";

    public static void main(String[] args) {
        try(Connection connection = getConnection(); Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE TEST_SPEED (ID NUMBER UNIQUE NOT NULL, SOME_STRING NVARCHAR2(50) NOT NULL, SOME_NUMBER NUMBER NOT NULL)");

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
