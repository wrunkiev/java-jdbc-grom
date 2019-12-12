package hibernate.homework2_2;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;


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
            String sql = "SELECT * FROM PRODUCTS WHERE ID = ?";
            product = (Product) session.createNativeQuery(sql).setParameter(1, id).getSingleResult();
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
    public List findByName(String name)throws Exception{
        if(name == null || name.isEmpty() || name.trim().isEmpty()){
            throw new Exception("name can't be null or empty");
        }
        Session session = null;
        Transaction tr = null;
        List products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT * FROM PRODUCTS WHERE NAME = ?";
            products = session.createNativeQuery(sql).setParameter(1, name).getResultList();
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
    public List findByContainedName(String name)throws Exception{
        if(name == null || name.isEmpty() || name.trim().isEmpty()){
            throw new Exception("name can't be null or empty");
        }

        Session session = null;
        Transaction tr = null;
        List products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT * FROM PRODUCTS WHERE NAME LIKE '%" + name + "%'";
            products = session.createNativeQuery(sql).getResultList();
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
    public List findByPrice(int price, int delta){
        int resultMin = price - delta;
        int resultPlus = price + delta;

        Session session = null;
        Transaction tr = null;
        List products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT * FROM PRODUCTS WHERE PRICE >= " + resultMin + " AND PRICE <= " + resultPlus;
            products = session.createNativeQuery(sql).getResultList();
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
    public List findByNameSortedAsc(String name)throws Exception{
        if(name == null || name.isEmpty() || name.trim().isEmpty()){
            throw new Exception("name can't be null or empty");
        }

        Session session = null;
        Transaction tr = null;
        List products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT * FROM PRODUCTS WHERE NAME LIKE '%" + name + "%' ORDER BY NAME";
            products = session.createNativeQuery(sql).getResultList();
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
    public List findByNameSortedDesc(String name)throws Exception{
        if(name == null || name.isEmpty() || name.trim().isEmpty()){
            throw new Exception("name can't be null or empty");
        }

        Session session = null;
        Transaction tr = null;
        List products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT * FROM PRODUCTS WHERE NAME LIKE '%" + name + "%' ORDER BY NAME DESC";
            products = session.createNativeQuery(sql).getResultList();
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
    public List findByPriceSortedDesc(int price, int delta){
        int resultMin = price - delta;
        int resultPlus = price + delta;

        Session session = null;
        Transaction tr = null;
        List products;
        try{
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT * FROM PRODUCTS WHERE PRICE >= " + resultMin + " AND PRICE <= " +
                    resultPlus + " ORDER BY PRICE DESC";
            products = session.createNativeQuery(sql).getResultList();
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

    private static SessionFactory createSessionFactory(){
        //singleton pattern
        if(sessionFactory == null) {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return sessionFactory;
    }
}
