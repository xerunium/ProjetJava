package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/details")
public class UserDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long client_id = Long.parseLong(request.getParameter("id"));
            Client client = clientService.findById(client_id);
            int countResa = reservationService.countByClientID(client_id);
            int countVehicle = reservationService.countVehicleByClientID(client_id);
            request.setAttribute("countResa", countResa);
            request.setAttribute("client", client);
            request.setAttribute("countvehicle", countVehicle);

            List<Reservation> listRes = reservationService.findByClientId(client_id);
            ArrayList<Vehicle> listVehicle = new ArrayList<>();
            System.out.println(listRes);
            if(!listRes.isEmpty()) {
                for (Reservation r : listRes) {
                    Vehicle vehicle = vehicleService.findById(r.getVehicule_id());
                    r.setVehicleName(vehicle.getConstructeur() + " " + vehicle.getModele());
                    if(!listVehicle.contains(vehicle)){
                        listVehicle.add(vehicle);
                    }
                }
            }
            System.out.println(listVehicle);
            request.setAttribute("vehicles", listVehicle);
            request.setAttribute("reservations", listRes);
        } catch (ServiceException e) {
            e.getMessage();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
    }
}
