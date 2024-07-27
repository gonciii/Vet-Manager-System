package dev.patika.vet.manager.system.core.exception;

public class ScheduleConflictException extends RuntimeException {

    public ScheduleConflictException(String message) {
        // Zamanlama çakışması olduğunda fırlatılan özel hata sınıfı
        super(message);
    }

    public ScheduleConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}