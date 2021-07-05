package com.epam.context;

import com.epam.entity.BaseEntity;
import com.epam.exception.UnknownEntityException;

import java.util.Collection;

public interface ApplicationContext {
    <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass);

    void init() throws UnknownEntityException;
}
