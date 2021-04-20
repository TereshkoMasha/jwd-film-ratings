package com.epam.entity;

public abstract class AbstractBaseEntity implements BaseEntity {

    private int id;
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getID() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
