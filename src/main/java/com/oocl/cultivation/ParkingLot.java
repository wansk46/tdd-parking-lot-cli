package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final int capacity;
    private Map<ParkingTicket, Car> cars = new HashMap<>();

    public ParkingLot() {
        this(10);
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public Map<ParkingTicket, Car> getName() {
        return this.cars;
    }

    public int getAvailableParkingPosition() {
        return capacity - cars.size();
    }

    public double getAvailablePositionRate(){
        return (double) getAvailableParkingPosition() / (double) capacity;
    }
}
