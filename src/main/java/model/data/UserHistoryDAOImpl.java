package model.data;

import model.data.entities.History;
import model.data.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserHistoryDAOImpl implements UserHistoryDAO {

    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.instance.getSessionFactory();

    {
        clearTables();
    }

    @Override
    public User getUserById(String sessionId) {
        Session session = sessionFactory.openSession();
        //User user = session.get(User.class, sessionId);

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        try {
            return session.createQuery(
                    criteriaQuery
                            .select(userRoot)
                            .where(criteriaBuilder.equal(userRoot.get("sessionId"), sessionId))
            ).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        //session.close();
        //return user;
    }

    @Override
    public void saveUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void saveHistory(History history) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(history);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteHistory(History history) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(history);
        transaction.commit();
        session.close();
    }

    @Override
    public void clearTables() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from model.data.entities.User").executeUpdate();
        session.createQuery("delete from model.data.entities.History").executeUpdate();
        transaction.commit();
        session.close();
    }
}
