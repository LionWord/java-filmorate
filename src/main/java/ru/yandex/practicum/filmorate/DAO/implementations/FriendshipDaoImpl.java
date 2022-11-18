package ru.yandex.practicum.filmorate.DAO.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.DAO.FriendshipDao;
import ru.yandex.practicum.filmorate.DAO.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FriendshipDaoImpl implements FriendshipDao {

    JdbcTemplate jdbcTemplate;
    UserDao userDao;

    @Autowired
    public FriendshipDaoImpl(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public boolean sendFriendshipRequest(int senderID, int recipientID) {
        String sql = "insert into FRIENDSHIP_REQUESTS (RECIPIENT_ID, SENDER_ID) values (?, ?)";
        try {
            jdbcTemplate.update(sql, recipientID, senderID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Map<Integer, Integer> acceptFriendshipRequest(int acceptorID, int requesterID) {
        String sql = "delete from FRIENDSHIP_REQUESTS where RECIPIENT_ID = ? and SENDER_ID = ?;" +
                "insert into FRIENDS (USER_ID, FRIEND_ID) values (?, ?)";
        try {
            jdbcTemplate.update(sql, acceptorID, requesterID, acceptorID, requesterID);
        } catch (Exception e) {
            return Map.of();
        }
        return Map.of(acceptorID, requesterID);
    }

    @Override
    public Optional<List<User>> getAllUserFriends(int userID) {
        String sql = "select u.* from USER_INFO as u " +
                "inner join FRIENDS as f on f.USER_ID = u.ID " +
                "where u.ID = (select f.FRIEND_ID from f where USER_ID = ?)";
        return Optional.ofNullable(jdbcTemplate.query(sql, (rs, rowNum) -> userDao.makeUser(rs), userID));
    }

    @Override
    public Optional<List<User>> getCommonFriends(int firstUserID, int secondUserID) {
        String sql = "select u.* from USER_INFO as u " +
                "inner join FRIENDS as f on f.USER_ID = u.ID " +
                "where u.ID = (select f.FRIEND_ID from f where USER_ID = ?) " +
                "and u.ID = (select f.FRIEND_ID from f where USER_ID = ?)";
        return Optional.ofNullable(jdbcTemplate.query(sql, (rs, rowNum) -> userDao.makeUser(rs), firstUserID, secondUserID));
    }

    @Override
    public boolean removeFriends(int userID, int friendID) {
        String sql = "delete from FRIENDS where USER_ID = ? and FRIEND_ID = ?;";
        try {
            jdbcTemplate.update(sql, userID, friendID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
