package ru.andryss.weblab3.model.data;

import org.hibernate.SessionFactory;

public interface HibernateSessionFactoryUtil {
    SessionFactory getSessionFactory();
}
