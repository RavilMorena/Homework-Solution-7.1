package mediator;

import aircraft.Aircraft;
import aircraft.Operation;

import java.util.*;
import java.util.concurrent.*;

public class ControlTower implements TowerMediator {
    private List<Aircraft> participants = new CopyOnWriteArrayList<>();
    private Queue<Aircraft> landingQueue = new ConcurrentLinkedQueue<>();
    private Queue<Aircraft> takeoffQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void registerAircraft(Aircraft a) {
        participants.add(a);
    }

    @Override
    public void broadcast(String msg, Aircraft sender) {
        if ("MAYDAY".equals(msg)) {
            System.out.printf("*** EMERGENCY from %s! Clearing runway! ***%n", sender.id);
            for (Aircraft a : participants) {
                if (!a.equals(sender)) {
                    a.receive("Hold position! Emergency on runway.");
                }
            }
            System.out.printf("Runway granted immediately to %s for emergency landing.%n", sender.id);
            return;
        }
        for (Aircraft a : participants) {
            if (!a.equals(sender)) {
                a.receive(msg);
            }
        }
    }

    @Override
    public boolean requestRunway(Aircraft a) {
        synchronized (this) {
            if (a.isEmergency()) {
                return true;
            }
            if (a.operation == Operation.LANDING) {
                if (landingQueue.isEmpty()) {
                    System.out.printf("Runway clear. %s may land now.%n", a.id);
                    return true;
                } else {
                    landingQueue.add(a);
                    System.out.printf("%s queued for landing.%n", a.id);
                    return false;
                }
            } else {
                if (takeoffQueue.isEmpty() && landingQueue.isEmpty()) {
                    System.out.printf("Runway clear. %s may take off now.%n", a.id);
                    return true;
                } else {
                    takeoffQueue.add(a);
                    System.out.printf("%s queued for takeoff.%n", a.id);
                    return false;
                }
            }
        }
    }

    public void processNext() {
        synchronized (this) {
            if (!landingQueue.isEmpty()) {
                Aircraft next = landingQueue.poll();
                System.out.printf("Granting runway to %s for landing.%n", next.id);
            } else if (!takeoffQueue.isEmpty()) {
                Aircraft next = takeoffQueue.poll();
                System.out.printf("Granting runway to %s for takeoff.%n", next.id);
            } else {
                System.out.println("No aircraft waiting. Runway idle.");
            }
        }
    }
}