import com.jayway.awaitility.core.ConditionTimeoutException;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;

public abstract class AbstractTest {

  Set<String> classes = new HashSet<>();

  protected AbstractTest(String classOne, String... classes) {
    this.classes.add(classOne);
    for (String clazz : classes) {
      this.classes.add(clazz);
    }
  }

  @Test
  public void test() {
    try {
      //Stopwatch stopwatch = new Stopwatch();
      await().atMost(2, TimeUnit.SECONDS).catchUncaughtExceptions().until(getTask());
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

  protected abstract Runnable getTask();

  protected abstract String lastInput();

  protected String error(String actual) {
    return Common.error(
        new StringBuilder()
        .append(lastInput())
        .append("\nActual: \"")
        .append(Common.print(actual))
        .append('"')
        .toString());
  }
}
