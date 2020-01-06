package hibernate.homework4.controller;

import hibernate.homework4.model.Filter;
import hibernate.homework4.model.Room;
import hibernate.homework4.service.RoomService;

import java.util.Date;
import java.util.Set;

public class RoomController {
    private RoomService roomService = new RoomService();

    public Room save(Room room)throws Exception{
        checkRoomNull(room);
        return roomService.save(room);
    }

    public void delete(long id)throws Exception{
        roomService.delete(id);
    }

    public Room update(Room room)throws Exception {
        checkRoomNull(room);
        if(room.getId() == null){
            throw new Exception("Exception in method RoomController.update. Enter id for room.");
        }
        return roomService.update(room);
    }

    public Room findById(long id)throws Exception{
        return roomService.findById(id);
    }

    public Set<Room> findRooms(Filter filter){
        return roomService.findRooms(filter);
    }

    public void bookRoom(long roomId, long userId, Date dateFrom, Date dateTo)throws Exception{
        roomService.bookRoom(roomId, userId, dateFrom, dateTo);
    }

    public void cancelReservation(long roomId, long userId)throws Exception{
        roomService.cancelReservation(roomId, userId);
    }

    private static void checkRoomNull(Room room)throws Exception{
        if(room == null){
            throw new Exception("Exception in method RoomController.checkRoomNull. Room can't be null.");
        }
    }
}
