package hibernate.homework4.DAO;

import hibernate.homework4.model.Hotel;
import hibernate.homework4.model.Order;
import hibernate.homework4.model.Room;
import hibernate.homework4.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import javax.persistence.NoResultException;

public class OrderDAO {
    private static SessionFactory sessionFactory;

    public Order save(Order order)throws Exception{
        checkOrderNull(order);

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.save(order);

            tr.commit();
            return order;
        }catch (HibernateException e){
            System.err.println("Exception in method OrderDAO.save. Save order with ID: " + order.getId() + " is failed");
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
        Order order = findById(id);

        if(order == null){
            throw new Exception("Exception in method OrderDAO.delete. Order with ID: " + id + " is not defined in DB.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.delete(order);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Exception in method OrderDAO.delete. Delete order with ID: " + order.getId() + " is failed.");
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

    public Order update(Order order)throws Exception{
        checkOrderNull(order);

        if(order.getId() == null){
            throw new Exception("Exception in method OrderDAO.update. Enter id for order.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.update(order);

            tr.commit();
            return order;
        }catch (HibernateException e){
            System.err.println("Exception in method OrderDAO.update. Update order with ID: " + order.getId() + " is failed");
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

    public Order findById(long id){
        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            Order order = session.get(Order.class, id);

            tr.commit();
            return order;
        }catch (HibernateException e){
            System.err.println("Exception in method OrderDAO.findById. Order with ID: " + id + " is not defined in DB.");
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

    private static void checkOrderNull(Order order)throws Exception{
        if(order == null){
            throw new Exception("Exception in method OrderDAO.checkOrderNull. Order can't be null.");
        }
    }
}
