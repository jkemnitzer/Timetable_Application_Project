package de.hofuniversity.minf.stundenplaner.common.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String type;
    private final Long id;

    public NotFoundException(Class<?> type, Long id) {
        super("Class of type " + type.getName() + " with id " + id + " not found!");
        this.type = type.getName();
        this.id = id;
    }
}
