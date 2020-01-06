package hibernate.homework4.controller;

import hibernate.homework4.model.User;
import hibernate.homework4.service.UserService;

public class UserController {
    private UserService userService = new UserService();

    public User save(User user)throws Exception{
        checkUserNull(user);
        return userService.save(user);
    }

    public void delete(long id)throws Exception{
        userService.delete(id);
    }

    public User update(User user)throws Exception {
        checkUserNull(user);
        if(user.getId() == null){
            throw new Exception("Exception in method UserController.update. Enter id for user.");
        }
        return userService.update(user);
    }

    public User findById(long id)throws Exception{
        return userService.findById(id);
    }

    public User registerUser(User user)throws Exception{
        return userService.registerUser(user);
    }

    private static void checkUserNull(User user)throws Exception{
        if(user == null){
            throw new Exception("Exception in method UserController.checkUserNull. User can't be null.");
        }
    }
}
