package model.data;

import org.hibernate.SessionFactory;

public interface HibernateSessionFactoryUtil {
    SessionFactory getSessionFactory();
}
