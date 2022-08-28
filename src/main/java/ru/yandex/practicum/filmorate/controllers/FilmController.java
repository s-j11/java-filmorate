package ru.yandex.practicum.filmorate.controllers;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController{
    private int id = 1;
    private Map<Integer, Film> films = new HashMap<>();

    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Film> findAll() {

        List<Film> inventoryFilms = new ArrayList<>();
        for (Map.Entry<Integer, Film> entry : films.entrySet()){
            inventoryFilms.add(entry.getValue());
        }
        return inventoryFilms;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addFilm(@RequestBody @NonNull Film film) {
        LocalDate date = LocalDate.of(1895,12,28);
        film.setId(id);
        id++;
        log.error("Ошибки валидации при добавление фильма");
            try {
                if (films.containsKey(film.getId())) {
                throw new ValidationException("Tакого фильм уже существует");
                }else if(film.getName().isEmpty()) {
                    throw new ValidationException("Название фильма не указано");
                }else if(film.getDescription().length() > 200){
                    throw new ValidationException("Опесание фильно вышло за границы 200 символов");
                }else if(film.getReleaseDate().isBefore(date)){
                    throw new ValidationException("Дата релиза не может быть ранее 28 декабря 1895 года");
                }else if(film.getDuration() < 0){
                    throw new ValidationException("Продолжительность фильма в минутах должна быль " +
                            "неотрицательной и больше 0");
                } else {
                    films.put(film.getId(),film);
                }
            }catch (ValidationException e){
                e.getMessage();
            }
        return film;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@RequestBody @NonNull Film film) {
        LocalDate date = LocalDate.of(1895, 12, 28);
        log.error("Ошибки валидации при обновление фильма");
            try {
                if (!films.containsKey(film.getId())) {
                    throw new ValidationException("Нет такого фильма");
                }else if (film.getName().isEmpty()) {
                    throw new ValidationException("Название фильма не указано");
                } else if (film.getDescription().length() > 200) {
                    throw new ValidationException("Опесание фильно вышло за границы 200 символов");
                } else if (film.getReleaseDate().isBefore(date)) {
                    throw new ValidationException("Дата релиза не может быть ранее 28 декабря 1895 года");
                } else if (film.getDuration() < 0) {
                    throw new ValidationException("Продолжительность фильма в минутах должна быль " +
                            "неотрицательной и больше 0");
                } else {
                    films.put(film.getId(), film);
                }
            } catch (ValidationException e) {
                e.getMessage();
            }
        return film;
    }
}
