package com.epam.entity;

public abstract class AbstractBaseEntity implements BaseEntity {

    private int id;

    @Override
    public Integer getID() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
