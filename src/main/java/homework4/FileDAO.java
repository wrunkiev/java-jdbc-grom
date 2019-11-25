package homework4;

import java.sql.*;
import java.util.ArrayList;

public class FileDAO {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cfzzaolfgt6x.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "2210Dracon1983";

    public File save(File file)throws Exception{
        validateFile(file);

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO FILES VALUES (?, ?, ?, ?)");
            preparedStatement.setLong(1, file.getId());
            preparedStatement.setString(2, file.getName());
            preparedStatement.setString(3, file.getFormat());
            preparedStatement.setLong(4, file.getSize());

            preparedStatement.executeUpdate();
            return file;
        }catch (SQLException e){
            System.err.println("Something went wrong. Can't save file with id " + file.getId());
            e.printStackTrace();
        }
        return null;
    }

    public void delete(long id)throws Exception{
        if(findById(id) == null){
            throw new Exception("This file is not exist in DB");
        }

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM FILES WHERE ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.err.println("Something went wrong. File can't delete from DB");
            e.printStackTrace();
        }
    }

    public File update(File file)throws Exception{
        validateFile(file);

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FILES SET FILE_NAME = ?, FILE_FORMAT = ?, FILE_SIZE = ? WHERE ID = ?");
            preparedStatement.setString(1, file.getName());
            preparedStatement.setString(2, file.getFormat());
            preparedStatement.setLong(3, file.getSize());
            preparedStatement.setLong(4, file.getId());

            //проверим файл который мы хотим обновить, если он есть в хранилище
            File tempFile = findById(file.getId());
            Storage storage = tempFile.getStorage();
            if(storage != null){
                preparedStatement = connection.prepareStatement("SELECT * FROM FILES WHERE STORAGE_ID = ?");
                preparedStatement.setLong(1, storage.getId());
                ResultSet resultSet = preparedStatement.executeQuery();

                long sizeStorage = 0;
                while (resultSet.next()){
                    sizeStorage += resultSet.getLong(4);
                }

                if(sizeStorage + file.getSize() - tempFile.getSize() > storage.getStorageMaxSize()){
                    throw new Exception("Can't update file with id=" + file.getId() + " in storage with id=" + storage.getId());
                }
            }
            preparedStatement.executeUpdate();
            return file;
        }catch (SQLException e){
            System.err.println("Something went wrong. Can't update file with id " + file.getId());
            e.printStackTrace();
        }
        return null;
    }

    public File findById(long id)throws Exception{
        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FILES WHERE ID = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                long fileId = resultSet.getLong(1);
                String fileName = resultSet.getString(2);
                String fileFormat = resultSet.getString(3);
                long fileSize = resultSet.getLong(4);
                long fileStorageId = resultSet.getLong(5);

                StorageDAO storageDAO = new StorageDAO();
                Storage storage = storageDAO.findById(fileStorageId);

                return new File(fileId, fileName, fileFormat, fileSize, storage);
            }
        }catch (SQLException e){
            System.err.println("Something went wrong. File with id = " + id + " can't find");
            e.printStackTrace();
        }
        return null;
    }

    private void validateFile(File file)throws Exception{
        if(file == null){
            throw new Exception("File can't be null");
        }

        if(file.getId() == null){
            throw new Exception("Id of File can't be null");
        }

        if(file.getName() == null){
            throw new Exception("Name of File can't be null");
        }

        if(file.getFormat() == null){
            throw new Exception("Format of File can't be null");
        }

        if(findById(file.getId()).equals(file)){
            throw new Exception("This file is exist already in DB");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
