package com.epf.rentmanager.exception;

public class DaoException extends Exception {

    public DaoException(){
    }

    public DaoException(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return "Un problème est survenu lors de l'execution";
    }
}
