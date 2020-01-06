package hibernate.homework4.service;

import hibernate.homework4.DAO.OrderDAO;
import hibernate.homework4.model.Order;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();

    public Order save(Order order)throws Exception{
        checkOrderNull(order);
        return orderDAO.save(order);
    }

    public void delete(long id)throws Exception{
        orderDAO.delete(id);
    }

    public Order update(Order order)throws Exception {
        checkOrderNull(order);
        if(order.getId() == null){
            throw new Exception("Exception in method OrderService.update. Enter id for order.");
        }
        return orderDAO.update(order);
    }

    public Order findById(long id)throws Exception{
        return orderDAO.findById(id);
    }

    private static void checkOrderNull(Order order)throws Exception{
        if(order == null){
            throw new Exception("Exception in method OrderService.checkOrderNull. Order can't be null.");
        }
    }
}
