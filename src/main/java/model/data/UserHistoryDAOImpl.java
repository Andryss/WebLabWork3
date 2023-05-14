package model.data;

import model.data.entities.History;
import model.data.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@ManagedBean(name = "userHistoryDAO")
@ApplicationScoped
public class UserHistoryDAOImpl implements UserHistoryDAO {

    @ManagedProperty("#{hibernateSessionFactoryUtil}")
    private HibernateSessionFactoryUtil sessionFactoryUtil;
    public void setSessionFactoryUtil(HibernateSessionFactoryUtil sessionFactoryUtil) {
        this.sessionFactoryUtil = sessionFactoryUtil;
    }

    private SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        sessionFactory = sessionFactoryUtil.getSessionFactory();
        clearTables();
    }

    @Override
    public User getUserById(String sessionId) {
        Session session = sessionFactory.openSession();

        // Difficult code to get user by session id using STRANGE criteria API
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
        } finally {
            session.close();
        }
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
