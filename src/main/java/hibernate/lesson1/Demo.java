package hibernate.lesson1;

import org.hibernate.Session;

public class Demo {
    public static void main(String[] args) {
        save();
    }

    public static void save(){
        Session session = new HIbernateUtils().createSessionFactory().openSession();

        session.getTransaction().begin();
        Product product = new Product();
        product.setId(99);
        product.setName("table");
        product.setDescription("grey & blue");
        product.setPrice(70);

        session.save(product);

        session.getTransaction().commit();

        System.out.println("Done");

        session.close();
    }
}
