package homework5;

import lesson5.HIbernateUtils;
import org.hibernate.Session;

public class ProductRepository {
    public void save(Product product)throws Exception{
        checkProductInDB(product);

        Session session = new HIbernateUtils().createSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(product);
        session.getTransaction().commit();
        System.out.println("Product " + product.getId() + " is saved in DB successfully");
        session.close();
    }

    public void delete(long id)throws Exception{
        Product product = getProductFromDB(id);
        checkProductNull(product);

        Session session = new HIbernateUtils().createSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(product);
        session.getTransaction().commit();
        System.out.println("Product " + id + " is deleted from DB successfully");
        session.close();
    }

    public void update(Product product)throws Exception{
        checkProductNull(product);

        Product p = getProductFromDB(product.getId());
        if(p == null){
            throw new Exception("This product " + product.getId() + "is not in DB");
        }

        Session session = new HIbernateUtils().createSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(product);
        session.getTransaction().commit();
        System.out.println("Product " + product.getId() + " is updated in DB successfully");
        session.close();
    }

    private void checkProductNull(Product product)throws Exception{
        if(product == null){
            throw new Exception("Product can't be null");
        }

        if(product.getName() == null || product.getName().isEmpty()){
            throw new Exception("Name of Product can't be null or empty");
        }
    }

    private void checkProductInDB(Product product)throws Exception{
        checkProductNull(product);
        Product p = getProductFromDB(product.getId());
        if(p != null && p.getId() == product.getId()){
            throw new Exception("This product " + product.getId() + " is exist already in DB");
        }
    }

    private Product getProductFromDB(long id){
        Session session = new HIbernateUtils().createSessionFactory().openSession();
        return session.get(Product.class, id);
    }
}
