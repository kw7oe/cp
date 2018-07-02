import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.sql.Timestamp;

public class Runway {
  private String name;
  private ReentrantLock lock;
  private Condition canDepart;
  private Boolean isAvailable = true;
  private int departCount = 0;
  private int landingCount = 0;

  public Runway(String name) {
    this.name = name;
    this.lock = new ReentrantLock(true);
    this.canDepart = lock.newCondition();
  }

  // Depart
  public void depart(int aircraftID) throws InterruptedException {
    lock.lock();

    try {
      while (!isAvailable)
        canDepart.await();

      // Departing
      printTakeover(aircraftID, " for departing");
      isAvailable = false;

      try {
        int departingTimeTaken = 5000;

        // If no other aircraft is taking off
        if (lock.getQueueLength() == 0) {
          departingTimeTaken += randomMs();
        }

        Thread.sleep(departingTimeTaken);
      } catch (Exception e) {
        System.out.println(e);
      }

      // Departed
      printDepart(aircraftID);
      departCount += 1;
      isAvailable = true;
      canDepart.signal();

    } finally {
      lock.unlock();
    }
  }

  // Land
  public void land(int aircraftID) throws InterruptedException {
    lock.lock();

    try {
      while (!isAvailable)
        canDepart.await();

      // Landing
      printTakeover(aircraftID, " for landing");
      isAvailable = false;
      try {
        Thread.sleep(10000);
      } catch (Exception e) {
        System.out.println(e);
      }

      // Landed
      printLanding(aircraftID);
      landingCount += 1;
      isAvailable = true;
      canDepart.signal();
    } finally {
      lock.unlock();
    }
  }

  public int getDepartCount() {
    return departCount;
  }

  public int getLandingCount() {
    return landingCount;
  }

  public String name() {
    return name;
  }

  public void printWithTime(int id, String string, String end) {
    System.out.println("Aircraft " + id +
        string + name + " at " +
        Util.getCurrentTimestamp() + end);
  }

  public void printTakeover(int id, String action) {
    // For colored output, only works in terminal that support ASCI escaped
    // code, such as bash. Does not work in Windows cmd.

    // if (action == " for departing") {
    //   action = Util.green(action);
    // } else {
    //   action = Util.red(action);
    // }
    // printWithTime(id, Util.blue(" is taking the     "), action);

    printWithTime(id, " is taking the     ", action);
  }

  public void printDepart(int id) {
    // Colored output
    // printWithTime(id, Util.green(" departed from     "), "");

    printWithTime(id, " departed from     ", "");
  }

  public void printLanding(int id) {
    // Colored output
    // printWithTime(id, Util.red(" landed on         "), "");

    printWithTime(id, " landed on         ", "");
  }

  public int randomMs() {
    return (int)(Math.random() * 5 * 1000);
  }
}
