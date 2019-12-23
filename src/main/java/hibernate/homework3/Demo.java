package hibernate.homework3;

public class Demo {
    public static void main(String[] args) throws Exception{
        Room room = new Room();


        HotelDAO hotelDAO = new HotelDAO();
        Hotel hotel = new Hotel();
        hotel.setCity("test");
        hotel.setCountry("test");
        hotel.setName("test");
        hotel.setStreet("test");
        hotelDAO.save(hotel);
    }
}
