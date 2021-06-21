package com.epam.entity.enums;

import com.epam.exception.UnknownEntityException;

public enum Appraisal {
    AWESOME(5),
    GOOD(4),
    MEH(3),
    BAD(2),
    AWFUL(1);

    private final int stars;

    Appraisal(int stars) {
        this.stars = stars;
    }

    public Integer getId() {
        return stars;
    }

    public static Appraisal resolveGenreById(Integer id) {
        Appraisal[] values = values();
        for (Appraisal appraisal :
                values) {
            if (appraisal.getId().equals(id)) return appraisal;
        }
        throw new UnknownEntityException("Such id doesn't exist!");
    }

}
