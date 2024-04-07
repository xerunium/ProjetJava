package com.ensta.rentmanager;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class VehicleServiceTest {
    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleDao vehicleDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.vehicleDao.findAll()).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> vehicleService.findAll());
    }

    @Test
    public void create_should_succeed_with_valid_data() throws ServiceException, DaoException {
        // Given
        Vehicle vehicle = new Vehicle("Toyota", "Corolla", 5);
        when(vehicleDao.create(vehicle)).thenReturn(1L);
        // When
        long vehicleId = vehicleService.create(vehicle);
        // Then
        assertEquals(1L, vehicleId);
    }

    @Test
    public void create_should_fail_when_constructor_is_empty() {
        // Given
        Vehicle vehicle = new Vehicle("", "Corolla", 5);
        // When & Then
        assertThrows(ServiceException.class, () -> vehicleService.create(vehicle));
    }

    @Test
    public void create_should_fail_when_model_is_empty() {
        // Given
        Vehicle vehicle = new Vehicle("Toyota", "", 5);
        // When & Then
        assertThrows(ServiceException.class, () -> vehicleService.create(vehicle));
    }

    @Test
    public void create_should_fail_when_number_of_seats_is_out_of_range() {
        // Given
        Vehicle vehicle1 = new Vehicle("Toyota", "Corolla", 1);
        Vehicle vehicle2 = new Vehicle("Toyota", "Corolla", 10);
        // When & Then
        assertThrows(ServiceException.class, () -> vehicleService.create(vehicle1));
        assertThrows(ServiceException.class, () -> vehicleService.create(vehicle2));
    }
}
