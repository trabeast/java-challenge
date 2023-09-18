package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.controllers.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

public interface ControllerExceptionHandler {

    Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * Handle exceptions when no Employee with id is found.
     * {@link EmployeeController#updateEmployee}, {@link EmployeeController#deleteEmployee}, and {@link EmployeeController#getEmployee} causes this exception.
     */
    @ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class})
    default ResponseEntity<Response> handleNoDataExceptions(Exception e) {
        LOGGER.error("Employee not found!", e);
        return ResponseEntity.badRequest().body(
                new Response("Employee not found! Make sure you are using a valid employee ID."));
    }
}
