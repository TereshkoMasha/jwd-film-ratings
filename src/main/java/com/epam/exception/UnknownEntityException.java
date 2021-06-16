package com.epam.exception;

import java.util.Arrays;

public class UnknownEntityException extends RuntimeException {

    private final String entityName;
    private final transient Object[] args;

    public UnknownEntityException(String entityName) {
        super();
        this.entityName = entityName;
        this.args = null;
    }

    public UnknownEntityException(String entityName, Object[] args) {
        super();
        this.entityName = entityName;
        this.args = args;
    }

    @Override
    public String getMessage() {
        if (args == null) {
            return entityName;
        }
        return String.format("Can't create %s with values: %s%n",
                entityName, Arrays.toString(args));
    }
}
