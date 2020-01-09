package hibernate.homework4.model;

import hibernate.homework4.DAO.UserDAO;

public class Session {
    private static Session session;
    private User user;

    private Session(){

    }

    public static Session getSession() {
        if(session == null){
            session = new Session();
        }
        return session;
    }

    public User getUser() {
        return user;
    }

    public void login(String userName, String password)throws Exception{
        UserDAO userDAO = new UserDAO();

        if(userName == null || userName.isEmpty()) {
            throw new Exception("Exception in method Session.login. UserName can't be null or empty");
        }

        if(password == null || password.isEmpty()){
            throw new Exception("Exception in method Session.login. Password can't be null or empty");
        }

        User userLogin = userDAO.getUserByName(userName);

        if(userLogin == null){
            throw new Exception("Exception in method Session.login. You must have register");
        }

        if(!userLogin.getPassword().equals(password)){
            throw new Exception("Exception in method Session.login. You entered failed password.");
        }

        if(session.getUser() == null){
            user = userLogin;
        }else {
            throw new Exception("Exception in method Session.login. You can't login " + userName + " , because " +
                    session.getUser().getUserName() + " is login already");
        }
    }

    public void logout(){
        if(session.getUser() == null){
            return;
        }else {
            user = null;
            session = null;
        }
    }
}
