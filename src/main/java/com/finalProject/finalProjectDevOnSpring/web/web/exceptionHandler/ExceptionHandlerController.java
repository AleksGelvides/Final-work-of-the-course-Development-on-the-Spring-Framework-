package com.finalProject.finalProjectDevOnSpring.web.web.exceptionHandler;

import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.web.dto.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> commonApplicationException(ApplicationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ExceptionResponse(e.getLocalizedMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundException(ApplicationNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getCause().getLocalizedMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> missingServletRequestParameterException(Exception ex) {
        List<String> errMessageList = new ArrayList<>();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach(err -> {
                errMessageList.add(err.getDefaultMessage());
            });
        } else {
            errMessageList.add(ex.getLocalizedMessage());
        }
        String message = errMessageList.toString()
                .replace("[", "")
                .replace("]","");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(message, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> accessDeniedExceptionHandler(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponse(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
