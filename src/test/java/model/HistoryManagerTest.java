package model;

import model.data.entities.History;
import org.junit.Test;

import java.util.Iterator;
import java.util.SortedSet;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class HistoryManagerTest {

    HistoryManager historyManager = HistoryManager.instance;

    @Test
    public void getUserHistory() {

        String sessionId = "some session id";
        Request request1 = new Request(1.0, 1.0, 1.0);
        Request request2 = new Request(2.0, 2.0, 2.0);
        Request request3 = new Request(3.0, 3.0, 3.0);

        historyManager.addUserRequest(sessionId, request1);
        historyManager.addUserRequest(sessionId, request2);
        historyManager.addUserRequest(sessionId, request3);

        SortedSet<History> userHistory = historyManager.getUserHistory(sessionId);
        assertNotNull(userHistory);

        assertEquals(userHistory.size(), 3);

        Iterator<History> historyIterator = userHistory.iterator();

        assertTrue(historyIterator.hasNext());
        isEqual(historyIterator.next(), request3);

        assertTrue(historyIterator.hasNext());
        isEqual(historyIterator.next(), request2);

        assertTrue(historyIterator.hasNext());
        isEqual(historyIterator.next(), request1);

        assertFalse(historyIterator.hasNext());
    }

    private void isEqual(History history, Request request) {
        assertEquals(history.getX(), request.getX(), 1e-5);
        assertEquals(history.getY(), request.getY(), 1e-5);
        assertEquals(history.getR(), request.getR(), 1e-5);
    }
}