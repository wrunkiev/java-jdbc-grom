package hibernate.homework3;

import hibernate.lesson2.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HotelDAO {
    private static SessionFactory sessionFactory;

    public Hotel save(Hotel hotel)throws Exception{
        checkHotelNull(hotel);

        if(hotel.equals(getHotelFromDB(hotel.getId()))){
            throw new Exception("Hotel" + hotel.getId() + " is exist in DB already");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.save(hotel);

            tr.commit();
            System.out.println("Save hotel " + hotel.getId() + " is done");
        }catch (HibernateException e){
            System.err.println("Save hotel " + hotel.getId() + " is failed");
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
        return hotel;
    }

    public Hotel delete(long id){
        return null;
    }

    public Hotel update(Hotel hotel){
        return null;
    }

    public Hotel findById(long id){
        return null;
    }

    private static SessionFactory createSessionFactory(){
        //singleton pattern
        if(sessionFactory == null) {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return sessionFactory;
    }

    private static void checkHotelNull(Hotel hotel)throws Exception{
        if(hotel == null){
            throw new Exception("Hotel can't be null");
        }
    }

    private static Hotel getHotelFromDB(long id)throws Exception{
        Hotel h = null;

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            h = session.get(Hotel.class, id);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Hotel is not defined in DB");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return h;
        }finally {
            if(session != null){
                session.close();
            }
        }
        return h;
    }


}
