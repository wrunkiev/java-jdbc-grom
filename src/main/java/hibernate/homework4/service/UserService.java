package hibernate.homework4.service;

import hibernate.homework4.DAO.UserDAO;
import hibernate.homework4.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User save(User user)throws Exception{
        checkUserNull(user);
        return userDAO.save(user);
    }

    public void delete(long id)throws Exception{
        userDAO.delete(id);
    }

    public User update(User user)throws Exception {
        checkUserNull(user);
        if(user.getId() == null){
            throw new Exception("Exception in method UserService.update. Enter id for user.");
        }
        return userDAO.update(user);
    }

    public User findById(long id){
        return userDAO.findById(id);
    }

    public User registerUser(User user)throws Exception{
        return userDAO.registerUser(user);
    }

    private static void checkUserNull(User user)throws Exception{
        if(user == null){
            throw new Exception("Exception in method UserService.checkUserNull. User can't be null.");
        }
    }
}
