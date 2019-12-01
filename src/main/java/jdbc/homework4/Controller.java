package jdbc.homework4;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cfzzaolfgt6x.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "2210Dracon1983";

    public void put(Storage storage, File file)throws Exception{
        validateStorageNull(storage);
        validateFileNull(file);
        validateStorageHasFile(storage, file);
        validateStorageSupFormat(storage, file);

        try(Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            if(getStorageSize(storage) + file.getSize() > storage.getStorageMaxSize()){
                throw new Exception("Size of storage " + storage.getId() + " more then max");
            }
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FILES SET STORAGE_ID = ? WHERE ID = ?");

            preparedStatement.setLong(1, storage.getId());
            preparedStatement.setLong(2, file.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (SQLException e){
            System.err.println("Something went wrong. Can't save file with id=" + file.getId() + " into storage with id=" + storage.getId());
            e.printStackTrace();
        }
    }

    public void putAll(Storage storage, List<File> files)throws Exception{
        validateStorageNull(storage);

        if(files == null){
            throw new Exception("List of files can't be null");
        }

        long sizeFiles = 0;
        for (File f : files){
            validateFileNull(f);
            validateStorageHasFile(storage, f);
            validateStorageSupFormat(storage, f);
            sizeFiles += f.getSize();
        }

        try(Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            if(getStorageSize(storage) + sizeFiles > storage.getStorageMaxSize()){
                throw new Exception("Size of storage " + storage.getId() + " more then max");
            }

            for(File f : files){
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FILES SET STORAGE_ID = ? WHERE ID = ?");
                preparedStatement.setLong(1, storage.getId());
                preparedStatement.setLong(2, f.getId());
                preparedStatement.executeUpdate();
            }

            connection.commit();
        }catch (SQLException e){
            System.err.println("Something went wrong. Can't save files into storage with id=" + storage.getId());
            e.printStackTrace();
        }
    }

    public void delete(Storage storage, File file)throws Exception{
        validateStorageNull(storage);
        validateFileNull(file);

        if(storage.equals(file.getStorage())){
            try(Connection connection = getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FILES SET STORAGE_ID = null WHERE ID = ?");
                preparedStatement.setLong(1, file.getId());

                preparedStatement.executeUpdate();

            }catch (SQLException e){
                System.err.println("Something went wrong. Can't delete file with id=" + file.getId() + " from storage with id=" + storage.getId());
                e.printStackTrace();
            }
        }else{
            throw new Exception("Storage " + storage.getId() + " hasn't file " + file.getId());
        }
    }

    public void transferAll(Storage storageFrom, Storage storageTo)throws Exception{
        validateStorageNull(storageFrom);
        validateStorageNull(storageTo);
        List<File> filesFrom = getFilesFromStorage(storageFrom);

        putAll(storageTo, filesFrom);
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id)throws Exception{
        validateStorageNull(storageFrom);
        validateStorageNull(storageTo);

        FileDAO fileDAO = new FileDAO();
        File file = fileDAO.findById(id);

        validateStorageSupFormat(storageTo, file);
        validateStorageHasFile(storageTo, file);

        try(Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            if(getStorageSize(storageTo) + file.getSize() > storageTo.getStorageMaxSize()){
                throw new Exception("Size of storage " + storageTo.getId() + " more then max");
            }
            delete(storageFrom, file);

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FILES SET STORAGE_ID = ? WHERE ID = ?");
            preparedStatement.setLong(1, storageTo.getId());
            preparedStatement.setLong(2, id);

            preparedStatement.executeUpdate();

            connection.commit();

        }catch (SQLException e){
            System.err.println("Something went wrong. Can't execute to transfer file= " + id + " from storage= " + storageFrom.getId() + " to storage " + storageTo.getId());
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private void validateStorageHasFile(Storage storage, File file)throws Exception{
        validateStorageNull(storage);
        validateFileNull(file);

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FILES WHERE STORAGE_ID = ?");
            preparedStatement.setLong(1, storage.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                long fileId = resultSet.getLong(1);
                String fileName = resultSet.getString(2);
                String fileFormat = resultSet.getString(3);
                long fileSize = resultSet.getLong(4);
                long storageId = resultSet.getLong(5);

                StorageDAO storageDAO = new StorageDAO();
                Storage storageFile = storageDAO.findById(storageId);

                File fileStorage = new File(fileId, fileName, fileFormat, fileSize, storageFile);

                if(fileStorage.equals(file)){
                    throw new Exception("Storage with id=" + storage.getId() + " has already this file with id=" + file.getId());
                }
            }
        }catch (SQLException e){
            System.err.println("Something went wrong.");
            e.printStackTrace();
        }
    }

    private void validateStorageSupFormat(Storage storage, File file)throws Exception{
        validateStorageNull(storage);
        validateFileNull(file);

        String[] formats = storage.getFormatsSupported();
        boolean isFormat = false;
        for (String s : formats){
            if(s.equals(file.getFormat())){
                isFormat = true;
                break;
            }
        }
        if(!isFormat){
            throw new Exception("Storage " + storage.getId() + " can't support this format in file " + file.getId());
        }
    }

    private long getStorageSize(Storage storage)throws Exception{
        validateStorageNull(storage);

        long sizeStorage = 0;

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FILES WHERE STORAGE_ID = ?");
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, storage.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                long fileStorageSize = resultSet.getLong(4);
                sizeStorage += fileStorageSize;
            }

            connection.commit();
            return sizeStorage;
        }catch (SQLException e){
            System.err.println("Something went wrong. Can't get size of storage " + storage.getId());
            e.printStackTrace();
        }
        return sizeStorage;
    }

    private void validateStorageNull(Storage storage)throws Exception{
        if(storage == null){
            throw new Exception("Storage can't be null");
        }
    }

    private void validateFileNull(File file)throws Exception{
        if(file == null){
            throw new Exception("File can't be null");
        }
    }

    private List<File> getFilesFromStorage(Storage storage)throws Exception{
        validateStorageNull(storage);
        List<File> files = new ArrayList<>();

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FILES WHERE STORAGE_ID = ?");
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, storage.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                long fileId = resultSet.getLong(1);
                String fileName = resultSet.getString(2);
                String fileFormat = resultSet.getString(3);
                long fileSize = resultSet.getLong(4);
                long storageId = resultSet.getLong(5);

                StorageDAO storageDAO = new StorageDAO();
                Storage storageFile = storageDAO.findById(storageId);

                File file = new File(fileId, fileName, fileFormat, fileSize, storageFile);
                files.add(file);
            }

            connection.commit();

            return files;
        }catch (SQLException e){
            System.err.println("Something went wrong. Can't get files from storage " + storage.getId());
            e.printStackTrace();
        }
        return files;
    }
}
