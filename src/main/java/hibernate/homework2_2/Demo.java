package hibernate.homework2_2;

public class Demo {
    public static void main(String[] args)throws Exception{
        ProductDAO productDAO = new ProductDAO();
        System.out.println(productDAO.findByContainedName("test"));


    }
}
