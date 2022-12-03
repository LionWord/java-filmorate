package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.services.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaController {

    private final MpaService mpaService;

    @GetMapping({"{id}"})
    public MPA getMPA(@PathVariable(value = "id") int mpaID) {
        return mpaService.getMpaByID(mpaID);
    }

    @GetMapping
    public List<MPA> getAllMPA() {
        return mpaService.getAllMPA();
    }

}
