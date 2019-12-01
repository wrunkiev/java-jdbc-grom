package hibernate.lesson2;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class ProductDAO {
    private static SessionFactory sessionFactory;

    public static Product save(Product product)throws Exception{
        checkProductNull(product);

        if(product.equals(getProductFromDB(product.getId()))){
            throw new Exception("Product" + product.getId() + " is exist in DB already");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.save(product);

            tr.commit();
            System.out.println("Save product " + product.getId() + " is done");
        }catch (HibernateException e){
            System.err.println("Save product " + product.getId() + " is failed");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
        return product;
    }

    public static Product update(Product product)throws Exception{
        checkProductNull(product);

        if(getProductFromDB(product.getId()) == null){
            throw new Exception("Product " + product.getId() + " is not in DB");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.update(product);

            tr.commit();
            System.out.println("Update product " + product.getId() + " is done");
        }catch (HibernateException e){
            System.err.println("Update product " + product.getId() + " is failed");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
        return product;
    }

    public static Product delete(Product product)throws Exception{
        checkProductNull(product);

        if(getProductFromDB(product.getId()) == null){
            throw new Exception("Product " + product.getId() + " is not in DB");
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.delete(product);

            tr.commit();
            System.out.println("Delete product " + product.getId() + " is done");
        }catch (HibernateException e){
            System.err.println("Delete product " + product.getId() + " is failed");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return null;
        }finally {
            if(session != null){
                session.close();
            }
        }
        return product;
    }

    public static void saveAll(List<Product> products)throws Exception{
        checkProductsNull(products);

        for(Product p : products){
            checkProductNull(p);
            if(p.equals(getProductFromDB(p.getId()))){
                throw new Exception("This product " + p.getId() + " is exist in DB already");
            }
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            for(Product product : products){
                session.save(product);
            }

            tr.commit();
            System.out.println("Save list of product is done");
        }catch (HibernateException e){
            System.err.println("Save list of product is failed");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public static void updateAll(List<Product> products)throws Exception{
        checkProductsNull(products);

        for(Product p : products){
            checkProductNull(p);
            if(getProductFromDB(p.getId()) == null){
                throw new Exception("This product " + p.getId() + " is not exist in DB");
            }
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            for(Product product : products){
                session.update(product);
            }

            tr.commit();
            System.out.println("Update list of product is done");
        }catch (HibernateException e){
            System.err.println("Update list of product is failed");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public static void deleteAll(List<Product> products)throws Exception{
        checkProductsNull(products);

        for(Product p : products){
            checkProductNull(p);
            if(getProductFromDB(p.getId()) == null){
                throw new Exception("This product " + p.getId() + " is not exist in DB");
            }
        }

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            for(Product product : products){
                session.delete(product);
            }

            tr.commit();
            System.out.println("Update list of product is done");
        }catch (HibernateException e){
            System.err.println("Update list of product is failed");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public static SessionFactory createSessionFactory(){
        //singleton pattern
        if(sessionFactory == null) {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return sessionFactory;
    }

    private static void checkProductNull(Product product)throws Exception{
        if(product == null){
            throw new Exception("Product can't be null");
        }
    }

    private static Product getProductFromDB(long id)throws Exception{
        Product p = null;

        Session session = null;
        Transaction tr = null;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            p = session.get(Product.class, id);

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Product is not defined in DB");
            System.err.println(e.getMessage());
            if(tr != null) {
                tr.rollback();
            }
            return p;
        }finally {
            if(session != null){
                session.close();
            }
        }
        return p;
    }

    private static void checkProductsNull(List<Product> products)throws Exception{
        if(products == null){
            throw new Exception("List of product can't be null");
        }
    }
}
