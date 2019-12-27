package hibernate.homework3;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class RoomDAO {
    private static SessionFactory sessionFactory;

    public Room save(Room room)throws Exception{

        checkRoomNull(room);

        if(room.equals(getRoomFromDB(room.getId()))){
            throw new Exception("Room " + room.getId() + " is exist in DB already");
        }

        if(room.getHotel() == null){
            throw new Exception("Hotel for room " + room.getId() + " can't be null");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.save(room);

            tr.commit();
            System.out.println("Save room " + room.getId() + " is done");
        }catch (HibernateException e){
            System.err.println("Save room " + room.getId() + " is failed");
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
        return room;
    }

    public void delete(long id){
        Room room = getRoomFromDB(id);

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.delete(room);

            tr.commit();
            System.out.println("Delete room " + room.getId() + " is done");
        }catch (HibernateException e){
            System.err.println("Delete room " + room.getId() + " is failed");
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

        if(!room.equals(getRoomFromDB(room.getId()))){
            throw new Exception("Room " + room.getId() + " is not exist in DB");
        }

        if(room.getHotel() == null){
            throw new Exception("Room must have hotel!!!");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.update(room);

            tr.commit();
            System.out.println("Update room " + room.getId() + " is done");
        }catch (HibernateException e){
            System.err.println("Update room " + room.getId() + " is failed");
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
        return room;
    }

    public Room findById(long id){
        return getRoomFromDB(id);
    }


    private static SessionFactory createSessionFactory(){
        //singleton pattern
        if(sessionFactory == null) {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return sessionFactory;
    }

    private static void checkRoomNull(Room room)throws Exception{
        if(room == null){
            throw new Exception("Room can't be null");
        }
    }

    private static Room getRoomFromDB(long id){
        Room r = null;

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            r = session.get(Room.class, id);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Room is not defined in DB");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return r;
        }finally {
            if(session != null){
                session.close();
            }
        }
        return r;
    }
}
