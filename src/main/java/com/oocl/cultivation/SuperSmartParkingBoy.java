package com.oocl.cultivation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperSmartParkingBoy {

    private List<ParkingLot> parkingLots;
    private String lastErrorMessage;
    private Map<ParkingTicket, ParkingLot> maplot = new HashMap<>();

    public SuperSmartParkingBoy(ParkingLot parkingLot) {
        parkingLots = new ArrayList<ParkingLot>();
        parkingLots.add(parkingLot);
    }

    public void addParkingLot(ParkingLot parkingLot){
        parkingLots.add(parkingLot);
    }


    public ParkingTicket park(Car car) {
        // TODO: Please implement the method
        ParkingLot firstParkinglot = findMaxAvailablePositionRateParkingLot();
        if (firstParkinglot == null || firstParkinglot.getAvailableParkingPosition() == 0){
            lastErrorMessage = "The parking lot is full.";
            return null;
        }
        ParkingTicket ticket = new ParkingTicket();
        firstParkinglot.getName().put(ticket, car);
        maplot.put(ticket,firstParkinglot);
        lastErrorMessage = null;
        return ticket;
    }

    public ParkingLot findMaxAvailablePositionRateParkingLot(){
        double maxNumberOfEmptyPosition = 0.0;
        ParkingLot maxEmptyParkingLot = null;
        for (ParkingLot parkingLot : parkingLots){
            if (parkingLot.getAvailablePositionRate() > maxNumberOfEmptyPosition){
                maxNumberOfEmptyPosition = parkingLot.getAvailablePositionRate();
                maxEmptyParkingLot = parkingLot;
            }
        }
        return maxEmptyParkingLot;
    }

    public Car fetch(ParkingTicket ticket) {
        if (ticket == null) {
            lastErrorMessage = "Please provide your parking ticket.";
            return null;
        }
        ParkingLot firstParkinglot = this.maplot.get(ticket);
        if (firstParkinglot == null){
            lastErrorMessage = "Unrecognized parking ticket.";
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
