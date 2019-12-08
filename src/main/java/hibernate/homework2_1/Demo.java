package hibernate.homework2_1;

import java.util.Arrays;

public class Demo {
    public static void main(String[] args)throws Exception{
        ProductDAO productDAO = new ProductDAO();
        System.out.println(productDAO.findByPrice(30, 10));


    }
}
