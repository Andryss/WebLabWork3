package model.data;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSessionFactoryUtil {

    public static final HibernateSessionFactoryUtil instance = new HibernateSessionFactoryUtil();

    private SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            try {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(registry);
                throw new ExceptionInInitializerError(e);
            }
        }
        return sessionFactory;
    }

}
