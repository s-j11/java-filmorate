package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exseptions.NotFoundException;
import ru.yandex.practicum.filmorate.exseptions.ValidationException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public Collection<Film> getFavoriteFilms(int count){
        return inMemoryFilmStorage.getFilms().entrySet().stream().sorted((o1, o2) -> Integer.compare(o2.getValue()
                        .getLikes().size(), o1.getValue().getLikes().size())).limit(count).map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public void addLike(Long filmID, Long userID){
        Set<Long> users = new HashSet<>();
        users.add(userID);
        Film film = inMemoryFilmStorage.getFilms().get(filmID);
        film.setLikes(users);
        try {
            inMemoryFilmStorage.updateFilm(film);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLike(Long filmID, Long userID){
        Set<Long> users = new HashSet<>();
        users.remove(userID);
        Film film = inMemoryFilmStorage.getFilms().get(filmID);
        film.setLikes(users);
        try {
            inMemoryFilmStorage.updateFilm(film);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public Film getFilmByID(long id) throws NotFoundException {
        if (!inMemoryFilmStorage.getFilms().containsKey(id)) {
            throw new NotFoundException("Нет такого фильма");
        } else {
            Film film = inMemoryFilmStorage.getFilms().get(id);
            return film;
        }
    }
}
