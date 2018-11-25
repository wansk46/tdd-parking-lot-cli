package com.oocl.cultivation;

public class ParkingBoy {

    private final ParkingLot parkingLot;
    private String lastErrorMessage;

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingTicket park(Car car) {
        // TODO: Please implement the method
        if (this.parkingLot.getAvailableParkingPosition() == 0){
            lastErrorMessage = "The parking lot is full.";
            return null;
        }
        ParkingTicket ticket = new ParkingTicket();
        this.parkingLot.getName().put(ticket, car);
        lastErrorMessage = null;
        return ticket;
    }

    public Car fetch(ParkingTicket ticket) {
        // TODO: Please implement the method
        if (ticket == null) {
            lastErrorMessage = "Please provide your parking ticket.";
            return null;
        }
        if (this.parkingLot.getName().containsKey(ticket)){
            lastErrorMessage = null;

            Car car = this.parkingLot.getName().get(ticket);
            this.parkingLot.getName().remove(ticket);
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
