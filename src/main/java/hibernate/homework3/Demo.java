package hibernate.homework3;


import java.util.Date;

public class Demo {
    public static void main(String[] args) throws Exception{
        /*Hotel hotel = new Hotel();
        hotel.setStreet("Test1");
        hotel.setName("Test1");
        hotel.setCountry("Test1");
        hotel.setCity("Test1");*/



        HotelDAO hotelDAO = new HotelDAO();
       // hotelDAO.save(hotel);

        /*Room room = new Room();
        room.setDateAvailableFrom(new Date());
        room.setPrice(23);
        room.setPetsAllowed(1);
        room.setNumberOfGuests(2);
        room.setBreakfastIncluded(1);
        room.setHotel(hotel);*/

        RoomDAO roomDAO = new RoomDAO();
        //roomDAO.save(room);

        hotelDAO.delete(3);


    }
}
