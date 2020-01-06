package hibernate.homework4.controller;

import hibernate.homework4.model.Order;
import hibernate.homework4.service.OrderService;

public class OrderController {
    private OrderService orderService = new OrderService();

    public Order save(Order order)throws Exception{
        checkOrderNull(order);
        return orderService.save(order);
    }

    public void delete(long id)throws Exception{
        orderService.delete(id);
    }

    public Order update(Order order)throws Exception {
        checkOrderNull(order);
        if(order.getId() == null){
            throw new Exception("Exception in method OrderController.update. Enter id for order.");
        }
        return orderService.update(order);
    }

    public Order findById(long id)throws Exception{
        return orderService.findById(id);
    }

    private static void checkOrderNull(Order order)throws Exception{
        if(order == null){
            throw new Exception("Exception in method OrderController.checkOrderNull. Order can't be null.");
        }
    }
}
