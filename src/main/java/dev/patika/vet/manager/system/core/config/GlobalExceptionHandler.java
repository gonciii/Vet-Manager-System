package dev.patika.vet.manager.system.core.config;

import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.exception.ScheduleConflictException;
import dev.patika.vet.manager.system.core.result.Result;
import dev.patika.vet.manager.system.core.result.ResultData;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.core.utilies.ResultHelper;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result> handleNotFoundException(NotFoundException e) {
        logger.error("NotFoundException: {}", e.getMessage());
        return new ResponseEntity<>(ResultHelper.notFoundError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultData<List<String>>> handleValidationErrors(MethodArgumentNotValidException e) {
        List<String> validationErrorList = e.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        return ((FieldError) error).getDefaultMessage();
                    } else {
                        return error.getDefaultMessage();
                    }
                })
                .collect(Collectors.toList());

        logger.error("Validation error: {}", validationErrorList);
        return new ResponseEntity<>(ResultHelper.validateError(validationErrorList), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ScheduleConflictException.class)
    public ResponseEntity<Result> handleScheduleConflictException(ScheduleConflictException e) {
        logger.error("ScheduleConflictException: {}", e.getMessage());
        return new ResponseEntity<>(ResultHelper.scheduleConflictError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ResultData<String>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        logger.error("IllegalArgumentException: {}", ex.getMessage());
        ResultData<String> result = new ResultData<>(false, ex.getMessage(), "400", null);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResultData<String>> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Exception: ", ex); // Log the stack trace
        ResultData<String> result = new ResultData<>(false, Msg.INTERNAL_SERVER_ERROR, "500", null);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Result> handleValidationException(ValidationException e) {
        // Doğrulama hatalarını ele al
        return new ResponseEntity<>(ResultHelper.validationError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}