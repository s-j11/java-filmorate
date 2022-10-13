package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.models.Film;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private long id = 1;
    private Map<Long, Film> films = new HashMap<Long, Film>();
    LocalDate date = LocalDate.of(1895,12,28);

    public Map<Long, Film> getFilms() {
        return films;
    }

    @Override
    public List<Film> findAll() {
        List<Film> inventoryFilms = new ArrayList<>();
        for (Map.Entry<Long, Film> entry : films.entrySet()){
            inventoryFilms.add(entry.getValue());
        }
        return inventoryFilms;
    }

    @Override
    public Film addFilm(Film film){
        film.setId(id);
            films.put(film.getId(), film);
            id++;
        return film;
    }

    @Override
    public void deleteFilm(long filmID) {
        if (films.containsKey(filmID)) {
            throw new NotFoundException("Таково пользователя нет");
        } else {
            films.remove(filmID);
            id--;
        }
    }
    @Override
    public Film updateFilm(Film film)  {
                    films.put(film.getId(), film);
            return film;
        }

        public Collection<Film> getFavoritFilms(long count){
            if (count > 0) {
                Collection<Film> films = new ArrayList<>();
                Collection<Film> films2 = new ArrayList<>();
                if (count > 0) {
                    Map<Integer, Film> resultWithNull = new HashMap<>();
                    Map<Integer, Film> resultWithOutNull = new HashMap<>();
                    Map<Integer, Film> resultSumLikesIsEmpty = new HashMap<>();
                    Map<Integer, Film> resultSumLikes = new HashMap<>();
                    for (Film film : getFilms().values()) {
                        if (film.getLikes() == null) {
                            if (resultWithNull.put((int) film.getId(), film) != null) {
                                throw new IllegalStateException("Duplicate key");
                            }
                        }
                    }
                    for (Film film : getFilms().values()) {
                        if (film.getLikes() != null) {
                            if (resultWithOutNull.put((int) film.getId(), film) != null) {
                                throw new IllegalStateException("Duplicate key");
                            }
                        }
                    }

                    for (Film film : resultWithOutNull.values()) {
                        if (film.getLikes().isEmpty()) {
                            if (resultSumLikesIsEmpty.put((int) film.getId(), film) != null) {
                                throw new IllegalStateException("Duplicate key");
                            }
                        }
                    }
                    for (Film film : resultWithOutNull.values()) {
                        if (!film.getLikes().isEmpty()) {
                            if (resultSumLikes.put((int) film.getId(), film) != null) {
                                throw new IllegalStateException("Duplicate key");
                            }
                        }
                    }
                    if ((resultWithNull.size() + resultSumLikesIsEmpty.size()) == getFilms().size()) {
                        films = resultWithNull.values().stream().collect(Collectors.toList());
                        films2 = resultSumLikesIsEmpty.values().stream().collect(Collectors.toList());
                        for (Film film : films) {
                            films2.add(film);
                        }
                        return films2;
                    } else if (resultSumLikes.size() == 1) {
                        return resultSumLikes.values().stream().collect(Collectors.toList());

                    } else if (resultSumLikes.size() > 1) {
                        return getFilms().values().stream().sorted(new FilmComporator())
                                .limit(count).collect(Collectors.toList());
                    }
                } else {
                    throw new NotFoundException("Ошибка списка популярных фильмов");
                }
            }
            return null;
        }

    static class FilmComporator implements Comparator<Film>{
        @Override
        public int compare(Film o1, Film o2) {
            return Integer.compare(o2.getLikes().size(),o1.getLikes().size());
        }
    }
}
