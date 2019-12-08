package hibernate.homework2_1;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDAO {
    private static SessionFactory sessionFactory;

    // поиск продукта по id
    public Product findById(long id){
        Session session = null;
        Transaction tr = null;
        Product product;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String hql = "FROM Product P WHERE P.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            product = (Product) query.getSingleResult();
            tr.commit();
            return product;
        }catch (HibernateException e){
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
    }

    //поиск продуктов по имени
    public List<Product> findByName(String name)throws Exception{
        if(name == null || name.isEmpty() || name.trim().isEmpty()){
            throw new Exception("name can't be null or empty");
        }
        Session session = null;
        Transaction tr = null;
        List<Product> products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String hql = "FROM Product P WHERE P.name = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            products = query.getResultList();
            tr.commit();
            return products;
        }catch (HibernateException e){
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
    }

    //поиск продуктов, которые в своем имени содержать слово name
    public List<Product> findByContainedName(String name)throws Exception{
        if(name == null || name.isEmpty() || name.trim().isEmpty()){
            throw new Exception("name can't be null or empty");
        }

        Session session = null;
        Transaction tr = null;
        List<Product> products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String hql = "FROM Product P WHERE P.name LIKE '%" + name + "%'";
            Query query = session.createQuery(hql);
            products = query.getResultList();
            tr.commit();
            return products;
        }catch (HibernateException e){
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
    }

    //поиск продуктов по вилке цен price+-delta включительно
    public List<Product> findByPrice(int price, int delta){
        int resultMin = price - delta;
        int resultPlus = price + delta;

        Session session = null;
        Transaction tr = null;
        List<Product> products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String hql = "FROM Product P WHERE P.price >= " + resultMin + " AND P.price <= " + resultPlus;
            Query query = session.createQuery(hql);
            products = query.getResultList();
            tr.commit();
            return products;
        }catch (HibernateException e){
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
    }

    //поиск продуктов по имени, результат отсортирован по алфавитному порядку колонки name
    public List<Product> findByNameSortedAsc(String name)throws Exception{
        if(name == null || name.isEmpty() || name.trim().isEmpty()){
            throw new Exception("name can't be null or empty");
        }

        Session session = null;
        Transaction tr = null;
        List<Product> products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String hql = "FROM Product P WHERE P.name LIKE '%" + name + "%' ORDER BY P.name";
            Query query = session.createQuery(hql);
            products = query.getResultList();
            tr.commit();
            return products;
        }catch (HibernateException e){
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
    }

    //поиск продуктов по имени, результат отсортирован начиная с конца алфавита колонки name
    public List<Product> findByNameSortedDesc(String name)throws Exception{
        if(name == null || name.isEmpty() || name.trim().isEmpty()){
            throw new Exception("name can't be null or empty");
        }

        Session session = null;
        Transaction tr = null;
        List<Product> products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String hql = "FROM Product P WHERE P.name LIKE'%" + name + "%' ORDER BY P.name DESC";
            Query query = session.createQuery(hql);
            products = query.getResultList();
            tr.commit();
            return products;
        }catch (HibernateException e){
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
    }

    //поиск продуктов по вилке цен price+-delta включительно, результат отсортирован по убыванию цен
    public List<Product> findByPriceSortedDesc(int price, int delta){
        int resultMin = price - delta;
        int resultPlus = price + delta;

        Session session = null;
        Transaction tr = null;
        List<Product> products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String hql = "FROM Product P WHERE P.price >= " + resultMin + " AND P.price <= "
                    + resultPlus + " ORDER BY P.price DESC";
            Query query = session.createQuery(hql);
            products = query.getResultList();
            tr.commit();
            return products;
        }catch (HibernateException e){
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
    }

    public static SessionFactory createSessionFactory(){
        //singleton pattern
        if(sessionFactory == null) {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return sessionFactory;
    }
}
