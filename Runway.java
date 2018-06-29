import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.sql.Timestamp;

public class Runway {
  private String name;
  private ReentrantLock lock;
  private Condition canDepart;
  private Boolean isLanding = false;
  private Boolean isDeparting = false;
  private int departCount = 0;
  private int landingCount = 0;

  public Runway(String name) {
    this.name = name;
    this.lock = new ReentrantLock(true);
    this.canDepart = lock.newCondition();
  }

  public void depart(int aircraftID) throws InterruptedException {
    lock.lock();

    try {
      while (isLanding || isDeparting)
        canDepart.await();

      isDeparting = true;
      printDepart(aircraftID);
      try {
        Thread.sleep(5000);
      } catch (Exception e) {
        System.out.println(e);
      }

      departCount += 1;
      isDeparting = false;
      canDepart.signal();
    } finally {
      lock.unlock();
    }
  }

  public void land(int aircraftID) {
    lock.lock();

    try {
      printLanding(aircraftID);
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

  public void printDepart(int id) {
    System.out.println("Aircraft_" + id +
        " is departing in " + name + " at " +
        Util.getCurrentTimestamp());
  }

  public void printLanding(int id) {
    System.out.println("Aircraft_" + id +
        " is landing " + name + " at " +
        Util.getCurrentTimestamp());
  }

}
