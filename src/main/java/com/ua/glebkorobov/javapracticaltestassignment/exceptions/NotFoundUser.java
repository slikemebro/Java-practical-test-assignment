package com.ua.glebkorobov.javapracticaltestassignment.exceptions;

public class NotFoundUser extends RuntimeException {
    public NotFoundUser(String message) {
        super(message);
    }
}
