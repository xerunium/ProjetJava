package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class VehicleCreateServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicle/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
        try {
            String manufacturer = req.getParameter("manufacturer");
            String modele = req.getParameter("modele");
            int seats = Integer.parseInt(req.getParameter("seats"));
            VehicleService.getInstance().create(new Vehicle(manufacturer, modele, seats));
        } catch (ServiceException e) {
            e.getMessage();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
