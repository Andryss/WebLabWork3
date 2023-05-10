package model;

import model.data.entities.History;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Iterator;
import java.util.SortedSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HistoryManagerTest {

    static HistoryManager historyManager;

    @BeforeClass
    public static void setUp() {
        historyManager = HistoryManager.instance;
    }

    @Test
    public void whenAddRequests_thenUpdateUserHistory() {

        String sessionId = "some session id";
        Request request1 = new Request(1.0, 1.0, 1.0);
        Request request2 = new Request(2.0, 2.0, 2.0);
        Request request3 = new Request(3.0, 3.0, 3.0);

        System.out.println("Add user requests");
        historyManager.addUserRequest(sessionId, request1);
        historyManager.addUserRequest(sessionId, request2);
        historyManager.addUserRequest(sessionId, request3);

        System.out.println("Get history");
        SortedSet<History> userHistory = historyManager.getUserHistory(sessionId);
        assertNotNull(userHistory);

        assertEquals(userHistory.size(), 3);

        Iterator<History> historyIterator = userHistory.iterator();

        System.out.println("Check if history equals to added requests");
        assertThat(historyIterator.hasNext(), is(true));
        isEqual(historyIterator.next(), request3);

        assertThat(historyIterator.hasNext(), is(true));
        isEqual(historyIterator.next(), request2);

        assertThat(historyIterator.hasNext(), is(true));
        isEqual(historyIterator.next(), request1);

        assertThat(historyIterator.hasNext(), is(false));
    }

    private void isEqual(History history, Request request) {
        assertThat(history.getX(), is(request.getX()));
        assertThat(history.getY(), is(request.getY()));
        assertThat(history.getR(), is(request.getR()));
    }
}