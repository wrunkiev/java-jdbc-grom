package hibernate.homework4.DAO;

import hibernate.homework4.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import javax.persistence.NoResultException;

public class UserDAO{
    private static SessionFactory sessionFactory;

    public User save(User user)throws Exception{
        checkUserNull(user);

        existUser(user);

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.save(user);

            tr.commit();
            return user;
        }catch (HibernateException e){
            System.err.println("Exception in method UserDAO.save. Save user with ID: " + user.getId() + " is failed");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public void delete(long id)throws Exception{
        User user = findById(id);

        if(user == null){
            throw new Exception("Exception in method UserDAO.delete. User with ID: " + id + " is not defined in DB.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.delete(user);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Exception in method UserDAO.delete. Delete user with ID: " + user.getId() + " is failed.");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public User update(User user)throws Exception{
        checkUserNull(user);

        if(user.getId() == null){
            throw new Exception("Exception in method UserDAO.update. Enter id for user.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.update(user);

            tr.commit();
            return user;
        }catch (HibernateException e){
            System.err.println("Exception in method UserDAO.update. Update user with ID: " + user.getId() + " is failed");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public User findById(long id){
        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            User user = session.get(User.class, id);

            tr.commit();
            return user;
        }catch (HibernateException e){
            System.err.println("Exception in method UserDAO.findById. User with ID: " + id + " is not defined in DB.");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public User registerUser(User user)throws Exception{
        return save(user);
    }

    public void login(String userName, String password)throws Exception{
        if(userName == null || userName.isEmpty()){
            throw new Exception("Exception in method UserDAO.login. UserName can't be null or empty");
        }

        if(password == null || password.isEmpty()){
            throw new Exception("Exception in method UserDAO.login. Password can't be null or empty");
        }

        User user = getUserByName(userName);
        if(user == null){
            throw new Exception("Exception in method UserDAO.login. " +
                    "User with name: " + userName + " is not defined in DB. You must have register.");
        }
    }

    private User getUserByName(String userName)throws Exception{
        if(userName == null || userName.isEmpty()){
            throw new Exception("Exception in method UserDAO.login. UserName can't be null or empty");
        }

        Session session = null;
        Transaction tr = null;
        User u;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM USERS WHERE USER_NAME = ?";
            NativeQuery query = session.createNativeQuery(sql, User.class);
            query.setParameter(1, userName);

            u = (User)query.getSingleResult();

            tr.commit();
            return u;
        }catch (NoResultException ex){
            System.err.println(ex.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }catch (HibernateException e){
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    private static SessionFactory createSessionFactory(){
        if(sessionFactory == null) {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return sessionFactory;
    }

    private static void checkUserNull(User user)throws Exception{
        if(user == null){
            throw new Exception("Exception in method UserDAO.checkUserNull. User can't be null.");
        }
    }

    private static void existUser(User user) throws Exception{
        if(getUserFromDB(user) != null){
            throw new Exception("Exception in method UserDAO.existUser. User with ID: " +
                    getUserFromDB(user).getId() + " is exist in DB already");
        }
    }

    private static User getUserFromDB(User user){
        Session session = null;
        Transaction tr = null;
        User u;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM USERS WHERE USER_NAME = ?";
            NativeQuery query = session.createNativeQuery(sql, User.class);
            query.setParameter(1, user.getUserName());

            u = (User)query.getSingleResult();

            tr.commit();
            return u;
        }catch (NoResultException ex){
            System.err.println(ex.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }catch (HibernateException e){
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }
}