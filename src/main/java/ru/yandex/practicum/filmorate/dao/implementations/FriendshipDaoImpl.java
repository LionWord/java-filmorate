package ru.yandex.practicum.filmorate.dao.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FriendshipDaoImpl implements FriendshipDao {

    final JdbcTemplate jdbcTemplate;
    final UserDao userDao;

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
        String sql = "select * from USER_INFO as u " +
                "inner join FRIENDS as f on f.FRIEND_ID=u.ID " +
                "where f.USER_ID = ?";
        return Optional.of(jdbcTemplate.query(sql, (rs, rowNum) -> userDao.makeUser(rs), userID));
    }

    @Override
    public Optional<List<User>> getCommonFriends(int firstUserID, int secondUserID) {
        String sql = "select * from USER_INFO as uf " +
                "join " +
                "(select * " +
                " from FRIENDS " +
                " where USER_ID = ? " +
                ") as userOne " +
                "join " +
                "(select * " +
                " from FRIENDS " +
                " where USER_ID = ?) as userTwo " +
                "on userOne.FRIEND_ID = userTwo.FRIEND_ID " +
                "where uf.ID = userOne.FRIEND_ID and uf.ID = userTwo.FRIEND_ID ";
        try {
            return Optional.of(jdbcTemplate.query(sql, (rs, rowNum) -> userDao.makeUser(rs), firstUserID, secondUserID));
        } catch (Exception e) {
            return Optional.of(List.of());
        }
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
