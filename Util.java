import java.sql.Timestamp;

public class Util {
  public static Timestamp getCurrentTimestamp() {
    return new Timestamp(System.currentTimeMillis());
  }
}
