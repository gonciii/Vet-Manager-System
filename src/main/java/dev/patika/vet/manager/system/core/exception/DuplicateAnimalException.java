package dev.patika.vet.manager.system.core.exception;

public class DuplicateAnimalException extends RuntimeException {
    public DuplicateAnimalException(String message) {
        super(message);
    }
}