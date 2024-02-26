package com.epf.rentmanager.exception;

public class DaoException extends Exception {
    @Override
    public String getMessage() {
        return "Un problème est survenu lors de l'execution";
    }
}
