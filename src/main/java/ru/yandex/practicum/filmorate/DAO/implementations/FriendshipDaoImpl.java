package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.FriendshipDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FriendshipDaoImpl implements FriendshipDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendshipDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean sendFriendshipRequest(String senderEmail, String recipientEmail) {
        String sql = "insert into FRIENDSHIP_REQUESTS (RECIPIENT_EMAIL, SENDER_EMAIL) values (?, ?)";
        try {
            jdbcTemplate.update(sql, recipientEmail, senderEmail);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, String> acceptFriendshipRequest(String acceptorEmail, String requesterEmail) {
        String sql = "delete from FRIENDSHIP_REQUESTS where RECIPIENT_EMAIL = ? and SENDER_EMAIL = ?;" +
                "insert into FRIENDS (USER_EMAIL, FRIEND_EMAIL) values (?, ?)";
        try {
            jdbcTemplate.update(sql, acceptorEmail, requesterEmail, acceptorEmail, requesterEmail);
        } catch (Exception e) {
            return Map.of();
        }
        return Map.of(acceptorEmail, requesterEmail);
    }

    @Override
    public List<User> getAllUserFriends(String userEmail) {
        return null;
    }

    @Override
    public List<User> getCommonFriends(String firstUserEmail, String secondUserEmail) {
        return null;
    }
}
