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
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/vehicles/details")
public class VehicleDetailsServlet extends HttpServlet {

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
            long vehicle_id = Long.parseLong(request.getParameter("id"));
            Vehicle vehicle = vehicleService.findById(vehicle_id);
            int countResa = reservationService.countByVehicleID(vehicle_id);
            int countClient = reservationService.countClientByVehicleID(vehicle_id);
            request.setAttribute("countResa", countResa);
            request.setAttribute("vehicle", vehicle);
            request.setAttribute("countclient", countClient);

            List<Reservation> listRes = reservationService.findByVehicleId(vehicle_id);
            ArrayList<Client> listClient = new ArrayList<>();
            if(!listRes.isEmpty()) {
                for (Reservation r : listRes) {
                    Client client = clientService.findById(r.getClient_id());
                    r.setClientName(client.getPrenom() + " " + client.getNom());
                    Optional<Client> clientTrouve = listClient.stream()
                            .filter(c -> c.getID() == client.getID())
                            .findFirst();

                    if (!clientTrouve.isPresent()) {
                        System.out.println("Le client n'est pas present");
                        listClient.add(client);
                    }
                }
            }
            request.setAttribute("clients", listClient);
            request.setAttribute("reservations", listRes);

        } catch (ServiceException e) {
            e.getMessage();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);
    }
}
