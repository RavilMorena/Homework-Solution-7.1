package aircraft;

import mediator.TowerMediator;

public class Helicopter extends Aircraft {
    public Helicopter(String id, int fuel, TowerMediator tower) {
        super(id, fuel, tower);
    }

    @Override
    public void receive(String msg) {
        System.out.printf("[Helicopter %s] Rotor Alert: %s%n", id, msg);
    }
}