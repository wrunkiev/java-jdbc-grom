package hibernate.homework4.demo;


import hibernate.homework4.DAO.HotelDAO;
import hibernate.homework4.DAO.OrderDAO;
import hibernate.homework4.DAO.RoomDAO;
import hibernate.homework4.DAO.UserDAO;
import hibernate.homework4.model.*;

import java.util.Date;

public class Demo {
    public static void main(String[] args) throws Exception{


        /*User user = new User();
        user.setCountry("test");
        user.setPassword("test");
        user.setUserName("test2");
        user.setUserType(UserType.ADMIN);*/

        //userDAO.save(user);
        //System.out.println(userDAO.findById(1).getUserType());


        /*Hotel hotel = new Hotel();
        hotel.setStreet("test");
        hotel.setName("test");
        hotel.setCountry("test");
        hotel.setCity("test");*/

        //HotelDAO hotelDAO = new HotelDAO();
        //hotelDAO.save(hotel);




        RoomDAO roomDAO = new RoomDAO();
        Room room = roomDAO.findById(1);

        UserDAO userDAO = new UserDAO();
        User user = userDAO.findById(8);

        Order order = new Order();
        order.setMoneyPaid(room.getPrice());
        order.setUser(user);
        order.setRoom(room);


        OrderDAO orderDAO = new OrderDAO();
        orderDAO.save(order);

    }
}
