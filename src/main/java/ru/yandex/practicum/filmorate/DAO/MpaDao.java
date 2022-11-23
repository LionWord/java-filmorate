package ru.yandex.practicum.filmorate.DAO;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MpaDao {
    MPA getMpaByID(int mpaID);
    List<MPA> getAllMPA();
}
