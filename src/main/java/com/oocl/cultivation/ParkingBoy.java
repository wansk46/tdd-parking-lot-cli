package com.oocl.cultivation;

import java.util.ArrayList;
import java.util.List;

public class ParkingBoy {

//    private final ParkingLot parkingLot;
    private List<ParkingLot> parkingLots;
    private String lastErrorMessage;

    public ParkingBoy(ParkingLot parkingLot) {
        parkingLots = new ArrayList<ParkingLot>();
        parkingLots.add(parkingLot);
//        this.parkingLot = parkingLot;
    }

    public void addParkingLot(ParkingLot parkingLot){
        parkingLots.add(parkingLot);
    }


    public ParkingTicket park(Car car) {
        // TODO: Please implement the method
        ParkingLot firstParkinglot = this.parkingLots.get(0);
        if (firstParkinglot.getAvailableParkingPosition() == 0){
            lastErrorMessage = "The parking lot is full.";
            return null;
        }
        ParkingTicket ticket = new ParkingTicket();
        firstParkinglot.getName().put(ticket, car);
        lastErrorMessage = null;
        return ticket;
    }

//    public ParkingLot findAvailableParkingLot(){
//        for (int i=0, i )
//    }

    public Car fetch(ParkingTicket ticket) {
        // TODO: Please implement the method
        ParkingLot firstParkinglot = this.parkingLots.get(0);
        if (ticket == null) {
            lastErrorMessage = "Please provide your parking ticket.";
            return null;
        }
        if (firstParkinglot.getName().containsKey(ticket)){
            lastErrorMessage = null;

            Car car = firstParkinglot.getName().get(ticket);
            firstParkinglot.getName().remove(ticket);
            return car;

        } else {
            lastErrorMessage = "Unrecognized parking ticket.";
            return null;
        }

    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
