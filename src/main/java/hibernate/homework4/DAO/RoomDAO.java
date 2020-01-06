package hibernate.homework4.DAO;

import hibernate.homework4.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomDAO {
    private static SessionFactory sessionFactory;

    public Room save(Room room)throws Exception{
        checkRoomNull(room);

        if(room.getHotel() == null){
            throw new Exception("Exception in method RoomDAO.save. Hotel for room " + room.getId() + " can't be null");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.save(room);
            tr.commit();
            return room;
        }catch (HibernateException e){
            System.err.println("Exception in method RoomDAO.save. Save room with ID: " + room.getId() + " is failed");
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
        Room room = findById(id);

        if(room == null){
            throw new Exception("Exception in method RoomDAO.delete. Room with ID: " + id + " is not defined in DB.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.delete(room);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Exception in method RoomDAO.delete. Delete room with ID: " + room.getId() + " is failed.");
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

    public Room update(Room room)throws Exception{
        checkRoomNull(room);

        if(room.getHotel() == null){
            throw new Exception("Exception in method RoomDAO.update. Hotel for room " + room.getId() + " can't be null");
        }

        if(room.getId() == null){
            throw new Exception("Exception in method RoomDAO.update. Enter id for room.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.update(room);

            tr.commit();
            return room;
        }catch (HibernateException e){
            System.err.println("Exception in method RoomDAO.update. Update room with ID: " + room.getId() + " is failed");
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

    public Room findById(long id){
        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            Room room = session.get(Room.class, id);

            tr.commit();
            return room;
        }catch (HibernateException e){
            System.err.println("Exception in method RoomDAO.findById. Room with ID: " + id + " is not defined in DB.");
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

    public Set<Room> findRooms(Filter filter){
        Set<Room> result = new HashSet<>();

        if(filter == null){
            result.addAll(getAllRooms());
        }else {
            for(Room r : getAllRooms()){
                if(r.getNumberOfGuests() >= filter.getNumberOfGuests()
                        && r.getPrice() >= filter.getPrice()
                        && r.getBreakfastIncluded() == filter.getBreakfastIncluded()
                        && r.getPetsAllowed() == filter.getPetsAllowed()
                        && r.getDateAvailableFrom().getTime() >= filter.getDateAvailableFrom().getTime()){
                    result.add(r);
                }
            }
        }
        return result;
    }

    public void bookRoom(long roomId, long userId, Date dateFrom, Date dateTo)throws Exception{
        Room room = findById(roomId);
        if(room == null){
            throw new Exception("Exception in method RoomDAO.bookRoom. Room with ID: " + roomId + " is not defined in DB.");
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.findById(userId);
        if(user == null){
            throw new Exception("Exception in method RoomDAO.bookRoom. User with ID: " + userId + " is not defined in DB.");
        }

        Order order = getOrderByRoomId(roomId);
        if(order != null){
            if(room.getDateAvailableFrom().getTime() >= order.getDateFrom().getTime() &&
                    room.getDateAvailableFrom().getTime() <= order.getDateTo().getTime()){
                throw new Exception("Exception in method RoomDAO.bookRoom. This room with ID: " + roomId + " is booked already.");
            }
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            order = new Order();
            order.setUser(user);
            order.setRoom(room);
            order.setDateFrom(dateFrom);
            order.setDateTo(dateTo);
            order.setMoneyPaid(room.getPrice());

            OrderDAO orderDAO = new OrderDAO();
            orderDAO.save(order);

            room.setDateAvailableFrom(dateTo);
            update(room);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Exception in method RoomDAO.bookRoom. Room with ID: " + roomId + " is filed to book.");
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

    public void cancelReservation(long roomId, long userId)throws Exception{
        Room room = findById(roomId);
        if(room == null){
            throw new Exception("Exception in method RoomDAO.cancelReservation. Room with ID: " + roomId + " is not defined in DB.");
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.findById(userId);
        if(user == null){
            throw new Exception("Exception in method RoomDAO.cancelReservation. User with ID: " + userId + " is not defined in DB.");
        }

        Order order = getOrderByRoomIdAndUserId(roomId, userId);
        if(order == null){
            throw new Exception("Exception in method RoomDAO.cancelReservation. Order is not defined in DB.");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            OrderDAO orderDAO = new OrderDAO();
            orderDAO.delete(order.getId());

            RoomDAO roomDAO = new RoomDAO();
            room.setDateAvailableFrom(new Date());
            roomDAO.update(room);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Exception in method RoomDAO.cancelReservation. Room with ID: " + roomId + " is filed to cancelreservation.");
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

    private Order getOrderByRoomIdAndUserId(long roomId, long userId){
        Session session = null;
        Transaction tr = null;
        Order order;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM ORDERS WHERE ORDER_ROOM_ID = ? AND ORDER_USER_ID = ?";
            NativeQuery query = session.createNativeQuery(sql, Order.class);
            query.setParameter(1, roomId);
            query.setParameter(2, userId);
            order = (Order)query.getSingleResult();
            tr.commit();
            return order;
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

    private Order getOrderByRoomId(long roomId){
        Session session = null;
        Transaction tr = null;
        Order order;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM ORDERS WHERE ORDER_ROOM_ID = ?";
            NativeQuery query = session.createNativeQuery(sql, Order.class);
            query.setParameter(1, roomId);
            order = (Order)query.getSingleResult();
            tr.commit();
            return order;
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

    private List<Room> getAllRooms(){
        Session session = null;
        Transaction tr = null;
        List<Room> rooms;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            String sql = "SELECT * FROM ROOMS";
            NativeQuery query = session.createNativeQuery(sql, Hotel.class);

            rooms = (List<Room>)query.getResultList();

            tr.commit();
            return rooms;
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

    private static void checkRoomNull(Room room)throws Exception{
        if(room == null){
            throw new Exception("Exception in method RoomDAO.checkRoomNull. Room can't be null.");
        }
    }
}
