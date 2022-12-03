package ru.yandex.practicum.filmorate.storage.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDbStorage implements MpaStorage {

    private final MpaDao mpaDao;

    public MPA getMpaByID(int mpaID) {
        return mpaDao.getMpaByID(mpaID);
    }

    public List<MPA> getAllMPA() {
        return mpaDao.getAllMPA();
    }
}
