package homework4;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws Exception{
        //test of method put
        /*Controller controller = new Controller();
        StorageDAO storageDAO = new StorageDAO();
        FileDAO fileDAO = new FileDAO();
        File file = fileDAO.findById(1);
        Storage storage = storageDAO.findById(1);
        controller.put(null, null);
        controller.put(storage, null);
        controller.put(storage, file);*/

        // test of method putAll
        /*Controller controller = new Controller();
        StorageDAO storageDAO = new StorageDAO();
        FileDAO fileDAO = new FileDAO();
        Storage storage = storageDAO.findById(3);
        List<File> files = new ArrayList<>();
        File file2 = fileDAO.findById(2);
        File file3 = fileDAO.findById(3);
        File file4 = fileDAO.findById(4);
        files.add(file2);
        files.add(file3);
        files.add(file4);
        //controller.putAll(null, null);
        //controller.putAll(storage, null);
        controller.putAll(storage, files);*/

        //test of method delete
        /*Controller controller = new Controller();
        StorageDAO storageDAO = new StorageDAO();
        Storage storage = storageDAO.findById(3);
        FileDAO fileDAO = new FileDAO();
        File file = fileDAO.findById(2);
        //controller.delete(null, null);
        //controller.delete(storage, null);
        controller.delete(storage, file);*/

        //test of method transferAll
        /*Controller controller = new Controller();
        StorageDAO storageDAO = new StorageDAO();
        Storage storageFrom = storageDAO.findById(3);
        Storage storageTo = storageDAO.findById(4);
        //controller.transferAll(null, null);
        //controller.transferAll(storageFrom, null);
        controller.transferAll(storageFrom, storageTo);*/

        //test of method transferFile
        Controller controller = new Controller();
        StorageDAO storageDAO = new StorageDAO();
        Storage storageFrom = storageDAO.findById(1);
        Storage storageTo = storageDAO.findById(2);
        //controller.transferFile(null, null, 1);
        //controller.transferFile(storageFrom, null, 1);
        controller.transferFile(storageFrom, storageTo,1);




















    }
}
