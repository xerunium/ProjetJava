package com.epf.rentmanager.exception;

import java.sql.SQLException;

public class DaoException extends Exception {

    public DaoException(){
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "Un problème est survenu lors de l'execution";
    }
}
