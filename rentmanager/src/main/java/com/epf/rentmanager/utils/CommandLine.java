package com.epf.rentmanager.utils;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLine {

    private ClientService clientService;
    private VehicleService vehicleService;

    private CommandLine() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        this.clientService = context.getBean(ClientService.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);
    }

    public static void main(String[] args) {

    }

}
