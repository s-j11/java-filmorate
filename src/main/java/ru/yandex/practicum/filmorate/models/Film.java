package ru.yandex.practicum.filmorate.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    private long id;

    @NotEmpty(message = "Название не должно быть пустым")
    private String name;

    @Size(max = 200,message = "Описание не должно превышать 200 символов")
    private String description;

    @Past(message = "Дата выхода фильма должна быть позже 28 декабря 1895 года ")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

    @Null
    private Set<Long> likes;
}
