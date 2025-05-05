package aircraft;

import mediator.TowerMediator;

public class PassengerPlane extends Aircraft {
    public PassengerPlane(String id, int fuel, TowerMediator tower) {
        super(id, fuel, tower);
    }

    @Override
    public void receive(String msg) {
        System.out.printf("[PassengerPlane %s] Console: %s%n", id, msg);
    }
}