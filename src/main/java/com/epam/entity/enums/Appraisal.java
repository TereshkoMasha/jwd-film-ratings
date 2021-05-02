package com.epam.entity.enums;

public enum Appraisal {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    private final int stars;

    Appraisal(int stars) {
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }
}
