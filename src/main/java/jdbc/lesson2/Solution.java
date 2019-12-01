package jdbc.lesson2;

import java.sql.*;

public class Solution {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cfzzaolfgt6x.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "2210Dracon1983";

    public static void main(String[] args) {

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                System.out.println("Class " + JDBC_DRIVER + " not found");
                return;
            }

            saveProduct();

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCTS")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);

                    Product product = new Product(id, name,description, price);
                    System.out.println(product);
                }
            }
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static void saveProduct(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            int response = statement.executeUpdate("INSERT INTO PRODUCTS VALUES (999, 'toy', 'for children', 60)");
            System.out.println(response);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private void deleteProducts(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            int response = statement.executeUpdate("DELETE FROM PRODUCTS WHERE NAME <> 'toy'");
            System.out.println(response);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private void deleteProductsByPrice(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            int response = statement.executeUpdate("DELETE FROM PRODUCTS WHERE PRICE < 100");
            System.out.println(response);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
