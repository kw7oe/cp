import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    int aircraftNumber = 9; // Aircraft Numbers
    int runwayNumber   = 3; // Runway   Numbers

    ArrayList<Runway>   runways   = new ArrayList<Runway>();
    ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
    ArrayList<Thread>   threads   = new ArrayList<Thread>();

    // Create and add to an array
    for (int i = 0; i < runwayNumber; i++) {
      runways.add(new Runway("Runway " + i));
    }

    // Create and assign aircraft to runway and add to an array
    for (int i = 0; i < aircraftNumber; i++) {
      int index = i % runwayNumber;
      aircrafts.add(new Aircraft(i, runways.get(index)));
    }

    // Create thread and add to an array
    for (Aircraft aircraft : aircrafts) {
      threads.add(new Thread(aircraft));
    }

    // Start all the threads
    for (Thread thread : threads) {
      thread.start();
    }

    // Wait for Thread result
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    // Print Runway Depart/Landing Count
    for (Runway runway: runways) {
      System.out.println("========");
      System.out.println(runway.name());
      System.out.println("========");
      System.out.println("Depart Count: " + runway.getDepartCount());
      System.out.println("Landing Count: " + runway.getLandingCount());
      System.out.println("");
    }
  }
}
