package com.smartosc.training.exceptions;


import com.smartosc.training.constants.SystemConstants;
import com.smartosc.training.dtos.APIError;
import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.UserDTO;
import javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 05/06/2020 - 10:40 AM
 * @created_by Hieupv
 * @since 05/06/2020
 */
@ControllerAdvice
public class HandleError extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> notFoundException(Exception ex) {
        APIError apiError = new APIError();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setError(ex.getMessage());
        apiError.setMessage("");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
