package com.oocl.cultivation.test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.SuperSmartParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuperSmartParkingBoyTest {
    @Test
    void should_park_a_car_to_a_parking_lot_and_get_it_back() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = superSmartParkingBoy.park(car);
        Car fetched = superSmartParkingBoy.fetch(ticket);

        assertSame(fetched, car);
    }

    @Test
    void should_park_multiple_cars_to_a_parking_lot_and_get_them_back() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);
        Car firstCar = new Car();
        Car secondCar = new Car();

        ParkingTicket firstTicket = superSmartParkingBoy.park(firstCar);
        ParkingTicket secondTicket = superSmartParkingBoy.park(secondCar);

        Car fetchedByFirstTicket = superSmartParkingBoy.fetch(firstTicket);
        Car fetchedBySecondTicket = superSmartParkingBoy.fetch(secondTicket);

        assertSame(firstCar, fetchedByFirstTicket);
        assertSame(secondCar, fetchedBySecondTicket);
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);
        Car car = new Car();
        ParkingTicket wrongTicket = new ParkingTicket();

        ParkingTicket ticket = superSmartParkingBoy.park(car);

        assertNull(superSmartParkingBoy.fetch(wrongTicket));
        assertSame(car, superSmartParkingBoy.fetch(ticket));
    }

    @Test
    void should_query_message_once_the_ticket_is_wrong() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);
        ParkingTicket wrongTicket = new ParkingTicket();

        superSmartParkingBoy.fetch(wrongTicket);
        String message = superSmartParkingBoy.getLastErrorMessage();

        assertEquals("Unrecognized parking ticket.", message);
    }

    @Test
    void should_clear_the_message_once_the_operation_is_succeeded() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);
        ParkingTicket wrongTicket = new ParkingTicket();

        superSmartParkingBoy.fetch(wrongTicket);
        assertNotNull(superSmartParkingBoy.getLastErrorMessage());

        ParkingTicket ticket = superSmartParkingBoy.park(new Car());
        assertNotNull(ticket);
        assertNull(superSmartParkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = superSmartParkingBoy.park(car);

        assertNull(superSmartParkingBoy.fetch(null));
        assertSame(car, superSmartParkingBoy.fetch(ticket));
    }

    @Test
    void should_query_message_once_ticket_is_not_provided() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);

        superSmartParkingBoy.fetch(null);

        assertEquals(
                "Please provide your parking ticket.",
                superSmartParkingBoy.getLastErrorMessage());
    }

    @Test
    void should_not_fetch_any_car_once_ticket_has_been_used() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = superSmartParkingBoy.park(car);
        superSmartParkingBoy.fetch(ticket); //get the car
        assertNull(superSmartParkingBoy.fetch(ticket)); //get the car again, because it got once,so should be nothing here.
    }

    @Test
    void should_query_error_message_for_used_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket ticket = superSmartParkingBoy.park(car);
        superSmartParkingBoy.fetch(ticket);
        superSmartParkingBoy.fetch(ticket);

        assertEquals(
                "Unrecognized parking ticket.",
                superSmartParkingBoy.getLastErrorMessage()
        );
    }

    @Test
    void should_not_park_cars_to_parking_lot_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);

        superSmartParkingBoy.park(new Car());//park jor one car,so no lots anymore

        assertNull(superSmartParkingBoy.park(new Car()));
    }

    @Test
    void should_get_message_if_there_is_not_enough_position() {
        final int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);

        superSmartParkingBoy.park(new Car());
        superSmartParkingBoy.park(new Car());

        assertEquals("The parking lot is full.", superSmartParkingBoy.getLastErrorMessage());
    }

    @Test
    void should_park_cars_to_the_parking_lot_which_has_most_available_position_rate(){
        ParkingLot parkingLot1 = new ParkingLot(5);
        ParkingLot parkingLot2 = new ParkingLot(10);
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot1);
        superSmartParkingBoy.addParkingLot(parkingLot2);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();

        ParkingTicket ticket1 = superSmartParkingBoy.park(car1);
        assertEquals(4,parkingLot1.getAvailableParkingPosition()); // Rate 1: 80%, Rate 2: 100%
        ParkingTicket ticket2 = superSmartParkingBoy.park(car2);
        assertEquals(9,parkingLot2.getAvailableParkingPosition()); // Rate 1: 80%, Rate 2: 90%
        ParkingTicket ticket3 = superSmartParkingBoy.park(car3);
        assertEquals(8,parkingLot2.getAvailableParkingPosition()); // Rate 1: 80%, Rate 2: 80%


        Car fetchedCar1 = superSmartParkingBoy.fetch(ticket1);
        Car fetchedCar2 = superSmartParkingBoy.fetch(ticket2);
        Car fetchedCar3 = superSmartParkingBoy.fetch(ticket3);

        assertSame(car1, fetchedCar1);
        assertSame(car2, fetchedCar2);
        assertSame(car3, fetchedCar3);
    }


}
