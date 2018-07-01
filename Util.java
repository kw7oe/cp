import java.sql.Timestamp;

public class Util {
  public static Timestamp getCurrentTimestamp() {
    return new Timestamp(System.currentTimeMillis());
  }

  public static String escape(int number) {
    return "\u001B[" + number + "m";
  }

  public static String red(String string) {
    return escape(31) + string + escape(0);
  }

  public static String green(String string) {
    return escape(32) + string + escape(0);
  }

  public static String blue(String string) {
    return escape(34) + string + escape(0);
  }
}
