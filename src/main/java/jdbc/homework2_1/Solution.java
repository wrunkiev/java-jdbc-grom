package jdbc.homework2_1;
import java.sql.*;
import java.util.ArrayList;

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

            System.out.println(getAllProducts());
            System.out.println(getProductsByPrice());
            System.out.println(getProductsByDescription());

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static ArrayList<Product> getAllProducts(){
        ArrayList<Product> list = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCTS")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);

                    Product product = new Product(id, name,description, price);
                    list.add(product);
                }
            }
            return list;

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<Product> getProductsByPrice(){
        ArrayList<Product> list = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCTS")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);

                    if(price <= 100){
                        Product product = new Product(id, name,description, price);
                        list.add(product);
                    }
                }
            }
            return list;

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<Product> getProductsByDescription(){
        ArrayList<Product> list = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCTS")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);

                    if(description.length() > 50){
                        Product product = new Product(id, name,description, price);
                        list.add(product);
                    }
                }
            }
            return list;

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }
}
