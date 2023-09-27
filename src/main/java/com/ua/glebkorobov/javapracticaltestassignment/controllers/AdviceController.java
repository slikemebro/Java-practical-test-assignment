package com.ua.glebkorobov.javapracticaltestassignment.controllers;

import com.ua.glebkorobov.javapracticaltestassignment.exceptions.NotFoundUser;
import com.ua.glebkorobov.javapracticaltestassignment.exceptions.WrongDates;
import com.ua.glebkorobov.javapracticaltestassignment.exceptions.WrongValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {


    @ExceptionHandler(NotFoundUser.class)
    public ResponseEntity<String> handleNotFoundUser(NotFoundUser e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(WrongDates.class)
    public ResponseEntity<String> handleWrongDates(WrongDates e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(WrongValidation.class)
    public ResponseEntity<String> handleWrongValidation(WrongValidation e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
