package hibernate.homework4.demo;

import hibernate.homework4.DAO.HotelDAO;
import hibernate.homework4.model.Hotel;

public class DemoHotel {
    public static void main(String[] args) throws Exception{
        HotelDAO hotelDAO = new HotelDAO();
        Hotel hotel = new Hotel();
        hotel.setCity("test1");
        hotel.setCountry("test1");
        hotel.setName("test1");
        hotel.setStreet("test1");
        //hotel.setId((long)5);


        //hotelDAO.save(hotel);

        hotelDAO.update(hotel);
    }
}
