package hibernate.homework4.service;

import hibernate.homework4.DAO.HotelDAO;
import hibernate.homework4.model.Hotel;

public class HotelService {
    private HotelDAO hotelDAO = new HotelDAO();

    public Hotel save(Hotel hotel)throws Exception{
        checkHotelNull(hotel);
        return hotelDAO.save(hotel);
    }

    public void delete(long id)throws Exception{
        hotelDAO.delete(id);
    }

    public Hotel update(Hotel hotel)throws Exception {
        checkHotelNull(hotel);
        if(hotel.getId() == null){
            throw new Exception("Exception in method HotelService.update. Enter id for hotel.");
        }
        return hotelDAO.update(hotel);
    }

    public Hotel findById(long id)throws Exception{
        return hotelDAO.findById(id);
    }

    public Hotel findHotelByName(String name)throws Exception{
        if(name == null || name.isEmpty()){
            throw new Exception("Exception in method HotelService.findHotelByName. Name can't be null.");
        }
        return hotelDAO.findHotelByName(name);
    }

    public Hotel findHotelByCity(String city)throws Exception {
        if (city == null || city.isEmpty()) {
            throw new Exception("Exception in method HotelService.findHotelByCity. City can't be null.");
        }
        return hotelDAO.findHotelByCity(city);
    }

    private static void checkHotelNull(Hotel hotel)throws Exception{
        if(hotel == null){
            throw new Exception("Exception in method HotelService.checkHotelNull. Hotel can't be null.");
        }
    }
}
