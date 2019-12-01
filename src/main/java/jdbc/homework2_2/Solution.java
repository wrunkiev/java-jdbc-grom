package jdbc.homework2_2;

import java.sql.*;
import java.util.ArrayList;

public class Solution {
    /*Напишите методы:
void increasePrice() - увеличивает значение цены на 100, во всех продуктах, где цена меньше 970
void changeDescription() - удаляет последнее предложение с описания всех продуктов с длинной описания больше 100*/

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cfzzaolfgt6x.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "2210Dracon1983";

    public static void main(String[] args) {
        //try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                System.out.println("Class " + JDBC_DRIVER + " not found");
                return;
            }

            //increasePrice();
            changeDescription();

        //}catch (SQLException e){
        //    System.err.println("Something went wrong");
        //   e.printStackTrace();
        //}
    }

    private static void increasePrice(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            int response = statement.executeUpdate("UPDATE PRODUCTS SET PRICE = PRICE + 100 WHERE PRICE < 970");
            System.out.println(response);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static void changeDescription(){
        ArrayList<Product> products = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCTS WHERE LENGTH(DESCRIPTION) > 100")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);

                    Product product = new Product(id, name,description, price);
                    products.add(product);
                }
            }

            ArrayList<Product> changedProducts = changeProducts(products);

            for (Product p : changedProducts){
                PreparedStatement ps = connection.prepareStatement("UPDATE PRODUCTS SET DESCRIPTION = ? WHERE ID = ?");
                ps.setString(1, p.getDescription());
                ps.setLong(2, p.getId());
                ps.executeUpdate();
                ps.close();
            }

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static ArrayList<Product> changeProducts(ArrayList<Product> products){
        ArrayList<Product> list = new ArrayList<>();
        ArrayList<String> wordDescription = new ArrayList<>();
        for(Product p : products){
            String description = p.getDescription();
            String[] piecesDescription = description.split("\\.");
            for(String e : piecesDescription){
                wordDescription.add(e.trim());
            }
            wordDescription.remove(wordDescription.size()-1);

            String result = "";
            for(String s : wordDescription){
                result += s + ". ";
            }
            p.setDescription(result.trim());
            list.add(p);
            wordDescription.clear();
        }
        return list;
    }
}
