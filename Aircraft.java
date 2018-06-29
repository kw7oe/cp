import java.util.Random;

public class Aircraft implements Runnable {
  private Runway runway;
  private int id;
  private Random random;

  public Aircraft(int id, Runway runway) {
    this.id = id;
    this.runway = runway;
    this.random = new Random();
    System.out.println("Aircarft_" + id + " is created at " +
        Util.getCurrentTimestamp());
  }

  public void setRunway(Runway runway) {
    this.runway = runway;
  }

  public void run() {
    try {
      if (random.nextBoolean()) {
        this.runway.depart(id);
      } else {
        this.runway.land(id);
      }
    } catch (Exception e) {
      System.out.println(e);
    }

  }

}
