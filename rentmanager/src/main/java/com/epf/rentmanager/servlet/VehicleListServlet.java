package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VehicleListServlet extends HttpServlet{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Vehicle> list = VehicleService.getInstance().findAll();
            request.setAttribute("vehicleList", list);
        } catch (ServiceException e) {
            e.getMessage();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicle/list.jsp").forward(request, response);
    }

}
