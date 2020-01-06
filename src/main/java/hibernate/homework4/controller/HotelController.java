package hibernate.homework4.controller;

import hibernate.homework4.model.Hotel;
import hibernate.homework4.service.HotelService;

public class HotelController {
    private HotelService hotelService = new HotelService();

    public Hotel save(Hotel hotel)throws Exception{
        checkHotelNull(hotel);
        return hotelService.save(hotel);
    }

    public void delete(long id)throws Exception{
        hotelService.delete(id);
    }

    public Hotel update(Hotel hotel)throws Exception {
        checkHotelNull(hotel);
        if(hotel.getId() == null){
            throw new Exception("Exception in method HotelController.update. Enter id for hotel.");
        }
        return hotelService.update(hotel);
    }

    public Hotel findById(long id)throws Exception{
        return hotelService.findById(id);
    }

    public Hotel findHotelByName(String name)throws Exception{
        if(name == null || name.isEmpty()){
            throw new Exception("Exception in method HotelController.findHotelByName. Name can't be null.");
        }
        return hotelService.findHotelByName(name);
    }

    public Hotel findHotelByCity(String city)throws Exception {
        if (city == null || city.isEmpty()) {
            throw new Exception("Exception in method HotelController.findHotelByCity. City can't be null.");
        }
        return hotelService.findHotelByCity(city);
    }

    private static void checkHotelNull(Hotel hotel)throws Exception{
        if(hotel == null){
            throw new Exception("Exception in method HotelController.checkHotelNull. Hotel can't be null.");
        }
    }
}
