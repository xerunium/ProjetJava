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

@WebServlet("/rents/details")
public class ReservationDetailsServlet extends HttpServlet {

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
            long rent_id = Long.parseLong(request.getParameter("id"));
            Reservation reservation = reservationService.findById(rent_id);
            Client client = clientService.findById(reservation.getClient_id());
            reservation.setClientName(client.getPrenom() + " " + client.getNom());
            Vehicle vehicle = vehicleService.findById(reservation.getVehicule_id());
            reservation.setVehicleName(vehicle.getConstructeur() + " " + vehicle.getModele());
            request.setAttribute("rent", reservation);
            /*
            List<Reservation> listRes = reservationService.findByClientId(client_id);
            ArrayList<Vehicle> listVehicle = new ArrayList<>();
            if(!listRes.isEmpty()) {
                for (Reservation r : listRes) {
                    Vehicle vehicle = vehicleService.findById(r.getVehicule_id());
                    r.setVehicleName(vehicle.getConstructeur() + " " + vehicle.getModele());
                    Optional<Vehicle> clientTrouve = listVehicle.stream()
                            .filter(v -> v.getId() == vehicle.getId())
                            .findFirst();

                    if (!clientTrouve.isPresent()) {
                        listVehicle.add(vehicle);
                    }
                }
            }
            request.setAttribute("vehicles", listVehicle);
            request.setAttribute("reservations", listRes);

            */
        } catch (ServiceException e) {
            e.getMessage();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(request, response);
    }
}
