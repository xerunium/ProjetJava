package com.epf.rentmanager.exception;

public class ServiceException extends Exception{

    public ServiceException(){

    }
    public ServiceException(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return "une erreur est survenue dans l'integrité des données";
    }
}
