import com.jayway.awaitility.core.ConditionTimeoutException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;

@RunWith(Parameterized.class)
public class GnomeFoodTest {

  int[] gnomes;
  int[] portions;
  int[] expected;
  Runnable task = new Runnable() {
    @Override
    public void run() {
      int[] actual = new GnomeFood().find(gnomes, portions);
      assertEquals(error(String.valueOf(printArray(actual))), expected, actual);
    }
  };

  public GnomeFoodTest(int[] gnomes, int[] portions, int[] expected) {
    classes.add("GnomeFood");

    this.gnomes = gnomes;
    this.portions = portions;
    this.expected = expected;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {new int[]{3}, new int[]{9}, new int[]{0}},
        {new int[]{1, 2, 3}, new int[]{9, 8, 7}, new int[]{2, 1, 0}},
        {new int[]{4, 1, 3, 2}, new int[]{2, 1, 3, 4}, new int[]{3, 1, 2, 0}},
        {new int[]{4, 1, 3, 2, Integer.MAX_VALUE, 9, 5},
            new int[]{7, 1, 2, 5, 3, 4, 6},
            new int[]{5, 1, 4, 2, 0, 6, 3}},
    });
  }

  protected String lastInput() {
    return new StringBuilder()
        .append("Input: ")
        .append(printArray(gnomes))
        .append(", \n")
        .append(printArray(portions))
        .append("\nExpected: ")
        .append(printArray(expected))
        .toString();
  }


  // commons
  @Test
  public void test() {
    try {
      //Stopwatch stopwatch = new Stopwatch();
      await().atMost(2, TimeUnit.SECONDS).catchUncaughtExceptions().until(task);
      //System.out.println(TimeUnit.NANOSECONDS.toMillis(stopwatch.getTime()));
    } catch (ConditionTimeoutException e) {

      String msg = e.toString();
      msg = Common.hasError(msg) ? msg : Common.error("Timeout Error\n" + lastInput());

      System.err.println(msg);
      System.exit(-1);
    } catch (Throwable t) {
      //t.printStackTrace();
      StackTraceElement[] stackTrace = t.getStackTrace();
      StringBuilder buf = new StringBuilder();

      buf.append(t.fillInStackTrace()).append('\n');

      for (StackTraceElement line : stackTrace) {
        if (classes.contains(line.getClassName())) {
          buf.append(line).append('\n');
        }
      }
      buf.append(lastInput());

      System.err.println(Common.error(buf.toString()));
      System.exit(-1);
    }
  }


  /**
   * StdOut
   */
  Set<String> classes = new HashSet<>();

  public static String printArray(int[] arr) {
    return printArray(arr, new StringBuilder()).toString();
  }

  public static StringBuilder printArray(int[] arr, StringBuilder res) {
    res.append("[ ");
    int length = Math.min(1000, arr.length);

    for (int i = 0; i < length; i++) {
      res.append(arr[i]).append(", ");
    }
    if (length < arr.length) {
      res.append("...");
    }
    return res.append(']');
  }

  public String error(String actual) {
    return new StringBuilder()
            .append(START)
            .append(lastInput())
            .append("\nActual: \"")
            .append(print(actual))
            .append('"')
            .append(END)
            .toString();
  }

  public static final String START = "[codegym-test]";
  public static final String END = "[/codegym-test]";

  public String print(String input) {
    if (input != null && input.length() > 100) {
      return String.format("[length:%d] ", input.length()) +
          input.substring(0, 97).replace("\n", "\\n")
          + "...";
    }
    return input.replace("\n", "\\n");
  }

  public static boolean hasError(String msg) {
    return msg.contains(START);
  }
  /**
   * Asserts
   */
  public void assertEquals(String msg, int[] expected, int[] actual) {
    Assert.assertArrayEquals(msg, expected, actual);
  }
}
