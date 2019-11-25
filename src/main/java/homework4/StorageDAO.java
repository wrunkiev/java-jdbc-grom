package homework4;

import java.sql.*;
import java.util.ArrayList;

public class StorageDAO {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cfzzaolfgt6x.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "2210Dracon1983";

    public Storage save(Storage storage)throws Exception{
        validateStorage(storage);

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STORAGES VALUES (?, ?, ?, ?)");
            preparedStatement.setLong(1, storage.getId());
            String formatsSupported = arrToStr(storage.getFormatsSupported());
            preparedStatement.setString(2, formatsSupported);
            preparedStatement.setString(3, storage.getStorageCountry());
            preparedStatement.setLong(4, storage.getStorageMaxSize());

            preparedStatement.executeUpdate();
            return storage;
        }catch (SQLException e){
            System.err.println("Something went wrong. Can't save Storage with id " + storage.getId());
            e.printStackTrace();
        }
        return null;
    }

    public void delete(long id)throws Exception{
        if(findById(id) == null){
            throw new Exception("This Storage is not exist in DB");
        }

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM STORAGES WHERE ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.err.println("Something went wrong. Storage can't delete from DB");
            e.printStackTrace();
        }
    }

    public Storage update(Storage storage)throws Exception{
        validateStorage(storage);

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE STORAGES SET STORAGE_FORMAT_SUPP = ?, STORAGE_COUNTRY = ?, STORAGE_SIZE = ? WHERE ID = ?");

            String formatsSupported = arrToStr(storage.getFormatsSupported());
            preparedStatement.setString(1, formatsSupported);
            preparedStatement.setString(2, storage.getStorageCountry());
            preparedStatement.setLong(3, storage.getStorageMaxSize());
            preparedStatement.setLong(4, storage.getId());

            preparedStatement.executeUpdate();
            return storage;
        }catch (SQLException e){
            System.err.println("Something went wrong. Can't update Storage with id " + storage.getId());
            e.printStackTrace();
        }
        return null;
    }

    public Storage findById(long id)throws Exception{
        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM STORAGES WHERE ID = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                long storageId = resultSet.getLong(1);
                String storageFormat = resultSet.getString(2);
                String[] formatsSupported = strToArr(storageFormat);
                String storageCountry = resultSet.getString(3);
                long storageMaxSize = resultSet.getLong(4);

                return new Storage(storageId, formatsSupported, storageCountry, storageMaxSize);
            }
        }catch (SQLException e){
            System.err.println("Something went wrong. Storage with id = " + id + " can't find");
            e.printStackTrace();
        }
        return null;
    }

    private void validateStorage(Storage storage)throws Exception{
        if(storage == null){
            throw new Exception("Storage can't be null");
        }

        if(storage.getId() == null){
            throw new Exception("Id of Storage can't be null");
        }

        if(storage.getFormatsSupported() == null){
            throw new Exception("Support format of Storage can't be null");
        }

        if(findById(storage.getId()).equals(storage)){
            throw new Exception("This storage is exist already in DB");
        }
    }

    private String[] strToArr(String string)throws Exception{
        if(string == null || string.isEmpty() || string.trim().isEmpty()){
            throw new Exception("Input string can't be null or empty");
        }

        String[] temp = string.trim().split(",");
        ArrayList<String> list = new ArrayList<>();
        for(String s : temp){
            if(s != null && !s.isEmpty() && !s.trim().isEmpty()){
                list.add(s.trim());
            }
        }

        String[] result = new String[list.size()];

        int i = 0;
        for (String s : list){
            result[i] = s;
            i++;
        }

        return result;
    }

    private String arrToStr(String[] arr)throws Exception{
        if(arr == null){
            throw new Exception("arr can't be null");
        }

        String result = "";
        for(String s : arr){
            if(s != null && !s.isEmpty() && !s.trim().isEmpty()){
                result += s + ",";
            }
            if(arr[arr.length - 1].equals(s)){
                result += s;
            }
        }

        return result;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
