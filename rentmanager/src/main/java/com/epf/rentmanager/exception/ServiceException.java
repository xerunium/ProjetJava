package com.epf.rentmanager.exception;

public class ServiceException extends Exception{
    @Override
    public String getMessage() {
        return "une erreur est survenue dans l'integrité des données";
    }
}
