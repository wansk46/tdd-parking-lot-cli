package com.oocl.cultivation.test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.SmartParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartParkingBoyTest {
    @Test
    void should_park_a_car_to_a_parking_lot_and_get_it_back() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = smartParkingBoy.park(car);
        Car fetched = smartParkingBoy.fetch(ticket);

        assertSame(fetched, car);
    }

    @Test
    void should_park_multiple_cars_to_a_parking_lot_and_get_them_back() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);
        Car firstCar = new Car();
        Car secondCar = new Car();

        ParkingTicket firstTicket = smartParkingBoy.park(firstCar);
        ParkingTicket secondTicket = smartParkingBoy.park(secondCar);

        Car fetchedByFirstTicket = smartParkingBoy.fetch(firstTicket);
        Car fetchedBySecondTicket = smartParkingBoy.fetch(secondTicket);

        assertSame(firstCar, fetchedByFirstTicket);
        assertSame(secondCar, fetchedBySecondTicket);
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);
        Car car = new Car();
        ParkingTicket wrongTicket = new ParkingTicket();

        ParkingTicket ticket = smartParkingBoy.park(car);

        assertNull(smartParkingBoy.fetch(wrongTicket));
        assertSame(car, smartParkingBoy.fetch(ticket));
    }

    @Test
    void should_query_message_once_the_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);
        ParkingTicket wrongTicket = new ParkingTicket();

        smartParkingBoy.fetch(wrongTicket);
        String message = smartParkingBoy.getLastErrorMessage();

        assertEquals("Unrecognized parking ticket.", message);
    }

    @Test
    void should_clear_the_message_once_the_operation_is_succeeded() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);
        ParkingTicket wrongTicket = new ParkingTicket();

        smartParkingBoy.fetch(wrongTicket);
        assertNotNull(smartParkingBoy.getLastErrorMessage());

        ParkingTicket ticket = smartParkingBoy.park(new Car());
        assertNotNull(ticket);
        assertNull(smartParkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = smartParkingBoy.park(car);

        assertNull(smartParkingBoy.fetch(null));
        assertSame(car, smartParkingBoy.fetch(ticket));
    }

    @Test
    void should_query_message_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);

        smartParkingBoy.fetch(null);

        assertEquals(
                "Please provide your parking ticket.",
                smartParkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_has_been_used() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = smartParkingBoy.park(car);
        smartParkingBoy.fetch(ticket); //get the car
        assertNull(smartParkingBoy.fetch(ticket)); //get the car again, because it got once,so should be nothing here.
    }

    @Test
    void should_query_error_message_for_used_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = smartParkingBoy.park(car);
        smartParkingBoy.fetch(ticket);
        smartParkingBoy.fetch(ticket);

        assertEquals(
                "Unrecognized parking ticket.",
                smartParkingBoy.getLastErrorMessage()
        );
    }

    @Test
    void should_not_park_cars_to_parking_lot_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);

        smartParkingBoy.park(new Car());//park jor one car,so no lots anymore

        assertNull(smartParkingBoy.park(new Car()));
    }

    @Test
    void should_get_message_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);

        smartParkingBoy.park(new Car());
        smartParkingBoy.park(new Car());

        assertEquals("The parking lot is full.", smartParkingBoy.getLastErrorMessage());
    }

    @Test
    void should_park_cars_to_the_parking_lot_which_has_most_empty_parking_position(){
        final int capacity = 1;
        ParkingLot parkingLot1 = new ParkingLot(capacity);
        ParkingLot parkingLot2 = new ParkingLot();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot1);
        smartParkingBoy.addParkingLot(parkingLot2);
        Car car1 = new Car();
        Car car2 = new Car();


        ParkingTicket ticket1 = smartParkingBoy.park(car1);
        ParkingTicket ticket2 = smartParkingBoy.park(car2);

        assertEquals(1,parkingLot1.getAvailableParkingPosition());
        assertEquals(8,parkingLot2.getAvailableParkingPosition());


        Car fetchedCar1 = smartParkingBoy.fetch(ticket1);
        Car fetchedCar2 = smartParkingBoy.fetch(ticket2);

        assertSame(car1, fetchedCar1);
        assertSame(car2, fetchedCar2);
    }


}
