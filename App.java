import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    int aircraftNumber = 9;
    int runwayNumber   = 3;

    ArrayList<Runway>   runways   = new ArrayList<Runway>();
    ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
    ArrayList<Thread>   threads   = new ArrayList<Thread>();

    for (int i = 0; i < runwayNumber; i++) {
      runways.add(new Runway("Runway_" + i));
    }
    for (int i = 0; i < aircraftNumber; i++) {
      int index = i % runwayNumber;
      aircrafts.add(new Aircraft(i, runways.get(index)));
    }
    for (Aircraft aircraft : aircrafts) {
      threads.add(new Thread(aircraft));
    }

    for (Thread thread : threads) {
      thread.start();
    }

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

  // To generate random index for runway
  public static int randomIndex(int max) {
    return (int)(Math.random() * max);
  }

}
