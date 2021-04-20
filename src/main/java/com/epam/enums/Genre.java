package com.epam.entity;

import com.epam.exception.UnknownEntityException;
import lombok.Getter;

import java.util.Locale;

public enum Genre {
    ACTION(1),
    ADULT(2),
    ADVENTURE(3),
    ANIMATION(4),
    BIOGRAPHY(5),
    COMEDY(6),
    CRIME(7),
    DOCUMENTARY(8),
    DRAMA(9),
    FAMILY(10),
    FANTASY(11),
    GAME_SHOW(12),
    HISTORY(13),
    HORROR(14),
    LIFESTYLE(15),
    MUSIC(16),
    MUSICAL(17),
    MYSTERY(18),
    NEWS(19),
    REALITY_TV(20),
    ROMANCE(21),
    SCI_FI(22),
    SHORT(23),
    SPORT(24),
    TALK_SHOW(25),
    THRILLER(26),
    WAR(27),
    WESTERN(28);

    @Getter
    private final Integer id;

    Genre(Integer id) {
        this.id = id;
    }

    public static Genre resolveRoleById(Integer id) {
        Genre[] values = values();
        for (Genre genre :
                values) {
            if (genre.getId().equals(id)) return genre;
        }
        throw new UnknownEntityException("Such id doesn't exist!");
    }


    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }
}
