package model.data;

import model.data.entities.History;
import model.data.entities.User;

public interface UserHistoryDAO {
    User getUserById(String sessionId);
    void saveUser(User user);

    void saveHistory(History history);
    void deleteHistory(History history);
    void clearTables();
}
