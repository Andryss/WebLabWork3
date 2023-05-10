package model.data;

import model.data.entities.History;
import model.data.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.*;

import java.io.Serializable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HibernateSessionFactoryUtilTest {

    static HibernateSessionFactoryUtil sessionFactoryUtil;
    static SessionFactory sessionFactory;
    static Session session;

    @BeforeClass
    public static void setUp() {
        System.out.println("Get HibernateSessionFactoryUtil instance");
        sessionFactoryUtil = HibernateSessionFactoryUtil.instance;

        System.out.println("Get session factory");
        sessionFactory = sessionFactoryUtil.getSessionFactory();
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("Close session factory");
        if (sessionFactory != null) sessionFactory.close();
    }

    @Before
    public void openSession() {
        System.out.println("Open session");
        session = sessionFactory.openSession();
    }

    @After
    public void closeSession() {
        System.out.println("Close session");
        if (session != null) session.close();
    }

    @Test
    public void whenSaveUser_thenReturnId() {
        System.out.println("Running test create...");

        session.beginTransaction();
        Long id = (Long) session.save(new User("test create session id"));
        session.getTransaction().commit();

        assertNotNull(id);
        assertTrue(id > 0);
    }

    @Test
    public void whenFindUser_thenReturnEqualSessionId() {
        System.out.println("Running test read...");

        String sessionId = "test read session id";
        User newUser = new User(sessionId);

        session.beginTransaction();
        Long id = (Long) session.save(newUser);
        session.getTransaction().commit();

        User updatedUser = session.find(User.class, id);
        assertThat(updatedUser.getSessionId(), is(equalTo(sessionId)));
    }

    @Test
    public void whenUpdateUser_thenReturnEqualSessionId() {
        System.out.println("Running test update...");

        String sessionId = "test update session id";

        session.beginTransaction();
        Long id = (Long) session.save(new User(sessionId));
        session.getTransaction().commit();

        User savedUser = session.find(User.class, id);
        assertThat(savedUser.getSessionId(), is(equalTo(sessionId)));

        String newSessionId = "new test update session id";
        savedUser.setSessionId(newSessionId);

        session.beginTransaction();
        session.update(savedUser);
        session.getTransaction().commit();

        User updatedUser = session.find(User.class, id);
        assertThat(updatedUser.getSessionId(), is(equalTo(newSessionId)));
    }

    @Test
    public void whenDeleteUser_thenReturnNull() {
        System.out.println("Running test delete...");

        String sessionId = "test delete session id";

        session.beginTransaction();
        Long id = (Long) session.save(new User(sessionId));
        session.getTransaction().commit();

        User newUser = session.find(User.class, id);
        assertNotNull(newUser);

        session.beginTransaction();
        session.delete(newUser);
        session.getTransaction().commit();

        User deletedUser = session.find(User.class, id);
        assertNull(deletedUser);
    }
}