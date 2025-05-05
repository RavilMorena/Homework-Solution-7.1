package aircraft;

import mediator.TowerMediator;

public abstract class Aircraft {
    public String id;
    protected int fuelLevel;
    protected boolean emergency;
    public Operation operation;
    protected TowerMediator tower;

    public Aircraft(String id, int fuel, TowerMediator tower) {
        this.id = id;
        this.fuelLevel = fuel;
        this.tower = tower;
        this.emergency = false;
        tower.registerAircraft(this);
    }

    public void triggerEmergency() {
        this.emergency = true;
        sendMessage("MAYDAY");
    }

    public boolean requestRunway(Operation op) {
        this.operation = op;
        System.out.printf("[%s %s] requesting %s (Fuel: %d)%n",
                getClass().getSimpleName(), id, op, fuelLevel);
        return tower.requestRunway(this);
    }

    public void sendMessage(String msg) {
        tower.broadcast(msg, this);
    }

    public boolean isEmergency() {
        return emergency;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public abstract void receive(String msg);
}