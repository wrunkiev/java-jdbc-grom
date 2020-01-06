package hibernate.homework4.service;

import hibernate.homework4.DAO.RoomDAO;
import hibernate.homework4.model.Filter;
import hibernate.homework4.model.Room;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RoomService {
    private RoomDAO roomDAO = new RoomDAO();

    public Room save(Room room)throws Exception{
        checkRoomNull(room);
        return roomDAO.save(room);
    }

    public void delete(long id)throws Exception{
        roomDAO.delete(id);
    }

    public Room update(Room room)throws Exception {
        checkRoomNull(room);
        if(room.getId() == null){
            throw new Exception("Exception in method RoomService.update. Enter id for room.");
        }
        return roomDAO.update(room);
    }

    public Room findById(long id)throws Exception{
        return roomDAO.findById(id);
    }

    public Set<Room> findRooms(Filter filter){
        return roomDAO.findRooms(filter);
    }

    public void bookRoom(long roomId, long userId, Date dateFrom, Date dateTo)throws Exception{
        roomDAO.bookRoom(roomId, userId, dateFrom, dateTo);
    }

    public void cancelReservation(long roomId, long userId)throws Exception{
        roomDAO.cancelReservation(roomId, userId);
    }

    private static void checkRoomNull(Room room)throws Exception{
        if(room == null){
            throw new Exception("Exception in method RoomService.checkRoomNull. Room can't be null.");
        }
    }
}
