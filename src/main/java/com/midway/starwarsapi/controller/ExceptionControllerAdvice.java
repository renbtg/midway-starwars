package com.midway.starwarsapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;

@EnableWebMvc
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<Object> processException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        //ex.getParameter().

        var sb = new StringBuilder();
        sb.append("calories-api: Invalid value(s), errors=[");
        sb.append(errors);
        sb.append("]");

        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<Object> processException(MethodArgumentTypeMismatchException ex) {

        String sb = "midway-starwars-api: Invalid parameter, name=[" +
                ex.getName() +
                "] with value=[" +
                ((ex.getValue() == null) ? "" : ex.getValue().toString()) +
                "], expected parameter type=[" +
                ex.getParameter().getGenericParameterType() +
                "]";

        return new ResponseEntity<>(sb, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MissingServletRequestParameterException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<Object> processException(MissingServletRequestParameterException ex) {

        String sb = "midway-starwars-api: Missing parameter, name=[" +
                ex.getParameterName() +
                "], expected parameter type=[" +
                ex.getParameterType() +
                "]";

        return new ResponseEntity<>(sb, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected ResponseEntity<Object> processException(Exception ex) {

        String sb = "midway-starwars-api: Exception processing request, message=[" +
                ex.getMessage() +
                "], class=[" +
                ex.getClass().getName() +
                "]";

        return new ResponseEntity<>(sb, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
