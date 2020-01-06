package hibernate.homework4.DAO;

import hibernate.homework4.model.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import javax.persistence.NoResultException;

public class HotelDAO {
    private static SessionFactory sessionFactory;

    public Hotel save(Hotel hotel)throws Exception{
        checkHotelNull(hotel);

        existHotel(hotel);

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.save(hotel);

            tr.commit();
            return hotel;
        }catch (HibernateException e){
            System.err.println("Exception in method HotelDAO.save. Save hotel with ID: " + hotel.getId() + " is failed");
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
        Hotel hotel = findById(id);

        if(hotel == null){
            throw new Exception("Exception in method HotelDAO.delete. Hotel with ID: " + id + " is not defined in DB.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.delete(hotel);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Exception in method HotelDAO.delete. Delete hotel with ID: " + hotel.getId() + " is failed.");
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

    public Hotel update(Hotel hotel)throws Exception{
        checkHotelNull(hotel);

        if(hotel.getId() == null){
            throw new Exception("Exception in method HotelDAO.update. Enter id for hotel.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.update(hotel);

            tr.commit();
            return hotel;
        }catch (HibernateException e){
            System.err.println("Exception in method HotelDAO.update. Update hotel with ID: " + hotel.getId() + " is failed");
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

    public Hotel findById(long id){
        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            Hotel hotel = session.get(Hotel.class, id);

            tr.commit();
            return hotel;
        }catch (HibernateException e){
            System.err.println("Exception in method HotelDAO.findById. Hotel with ID: " + id + " is not defined in DB.");
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

    public Hotel findHotelByName(String name)throws Exception{
        if(name == null || name.isEmpty()){
            throw new Exception("Exception in method HotelDAO.findHotelByName. Name can't be null.");
        }

        Session session = null;
        Transaction tr = null;
        Hotel h;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM HOTELS WHERE HOTEL_NAME = ?";
            NativeQuery query = session.createNativeQuery(sql, Hotel.class);
            query.setParameter(1, name);

            h = (Hotel)query.getSingleResult();

            tr.commit();
            return h;
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

    public Hotel findHotelByCity(String city)throws Exception{
        if(city == null || city.isEmpty()){
            throw new Exception("Exception in method HotelDAO.findHotelByCity. City can't be null.");
        }

        Session session = null;
        Transaction tr = null;
        Hotel h;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM HOTELS WHERE HOTEL_CITY = ?";
            NativeQuery query = session.createNativeQuery(sql, Hotel.class);
            query.setParameter(1, city);

            h = (Hotel)query.getSingleResult();

            tr.commit();
            return h;
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

    private static void checkHotelNull(Hotel hotel)throws Exception{
        if(hotel == null){
            throw new Exception("Exception in method HotelDAO.checkHotelNull. Hotel can't be null.");
        }
    }

    private static void existHotel(Hotel hotel) throws Exception{
        if(getHotelFromDB(hotel) != null){
            throw new Exception("Exception in method HotelDAO.existHotel. Hotel with ID: " +
                    getHotelFromDB(hotel).getId() + " is exist in DB already");
        }
    }

    private static Hotel getHotelFromDB(Hotel hotel){
        Session session = null;
        Transaction tr = null;
        Hotel h;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM HOTELS WHERE HOTEL_NAME = ?";
            NativeQuery query = session.createNativeQuery(sql, Hotel.class);
            query.setParameter(1, hotel.getName());

            h = (Hotel)query.getSingleResult();

            tr.commit();
            return h;
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
