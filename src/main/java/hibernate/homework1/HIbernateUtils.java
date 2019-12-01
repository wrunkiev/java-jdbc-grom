package hibernate.homework1;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HIbernateUtils {
    private SessionFactory sessionFactory;

    public SessionFactory createSessionFactory(){
        return new Configuration().configure().buildSessionFactory();
    }
}
