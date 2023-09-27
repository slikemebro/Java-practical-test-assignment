package com.ua.glebkorobov.javapracticaltestassignment.exceptions;

public class WrongValidation extends RuntimeException {
    public WrongValidation(String message) {
        super(message);
    }
}
