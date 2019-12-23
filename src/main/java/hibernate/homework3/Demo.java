package hibernate.homework3;

public class Demo {
    public static void main(String[] args) throws Exception{



        HotelDAO hotelDAO = new HotelDAO();
        Hotel hotel = new Hotel();

        hotel.setCity("test");
        hotel.setCountry("test");
        hotel.setName("test");
        hotel.setStreet("test");


        Room room = new Room();

        room.setHotel(hotel);
        room.setBreakfastIncluded(1);
        room.setNumberOfGuests(1);
        room.setPetsAllowed(1);
        room.setPrice(2);

        hotelDAO.save(hotel);
    }
}
