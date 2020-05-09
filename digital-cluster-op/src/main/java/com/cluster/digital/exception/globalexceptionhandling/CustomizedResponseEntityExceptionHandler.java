package com.cluster.digital.exception.globalexceptionhandling;

import com.cluster.digital.exception.IdDoesNotExistsException;
import com.cluster.digital.model.response.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@ControllerAdvice
@RestController
@Slf4j
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{} {}", e.getMessage(), e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        List<String> errorList = new ArrayList<String>();
        for (ObjectError o : allErrors) {
            errorList.add(o.getDefaultMessage());
        }
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed", errorList.toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorDetails> handleUniqueException(Exception e, WebRequest request) {
        log.error("{} {}", e.getMessage(), e);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Duplicate entry, data is not unique.",
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IdDoesNotExistsException.class)
    public final ResponseEntity<ErrorDetails> handleNotFoundException(Exception e, WebRequest request) {
        log.error("{} {}", e.getMessage(), e);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleRootException(Exception e, WebRequest request) {
        log.error("{} {}", e.getMessage(), e);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}