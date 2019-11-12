package homework3_1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cfzzaolfgt6x.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "2210Dracon1983";

    public List<Product> findProductsByPrice(int price, int delta){
        List<Product> products = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCTS WHERE PRICE BETWEEN ?-? AND ?+?")) {
            preparedStatement.setInt(1,price);
            preparedStatement.setInt(2, delta);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, delta);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long productId = resultSet.getLong(1);
                    String productName = resultSet.getString(2);
                    String productDescription = resultSet.getString(3);
                    int productPrice = resultSet.getInt(4);

                    Product product = new Product(productId, productName, productDescription, productPrice);
                    products.add(product);
                }
            }
            return products;

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> findProductsByName(String word)throws Exception{
        validate(word);

        List<Product> products = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCTS WHERE NAME LIKE '%?%'")) {
            preparedStatement.setString(1, word);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long productId = resultSet.getLong(1);
                    String productName = resultSet.getString(2);
                    String productDescription = resultSet.getString(3);
                    int productPrice = resultSet.getInt(4);

                    Product product = new Product(productId, productName, productDescription, productPrice);
                    products.add(product);
                }
            }
            return products;

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    private void validate(String word)throws Exception{
        if(word.trim().length() < 3)
            throw new Exception("Length of word <<" + word + ">> less then 3");

        String[] temp = word.split(" ");

        if(temp.length > 1)
            throw new Exception("Word <<" + word + ">> contains more then 1 word");

        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(word);
        if(m.find())
            throw new Exception("Word <<" + word + ">> contains special characters");
    }

    public List<Product> findProductsWithEmptyDescription(){
        List<Product> products = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCTS WHERE DESCRIPTION IS NULL OR DESCRIPTION = ?")) {
            preparedStatement.setString(1, "");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long productId = resultSet.getLong(1);
                    String productName = resultSet.getString(2);
                    String productDescription = resultSet.getString(3);
                    int productPrice = resultSet.getInt(4);

                    Product product = new Product(productId, productName, productDescription, productPrice);
                    products.add(product);
                }
            }
            return products;

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
