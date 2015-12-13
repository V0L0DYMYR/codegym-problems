import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class KMPTest  extends AbstractTest {

  Runnable task = new Runnable() {
    @Override
    public void run() {
      int actual = new KMP().indexOf(inputPattern, inputStr);
      Common.assertCondition(actual == expected, error(String.valueOf(actual)));
    }
  };

  private String inputPattern;
  private String inputStr;
  private int expected;

  public KMPTest (String inputStr, String inputPattern, int expected) {
    super("KMP");
    this.inputPattern = inputPattern;
    this.inputStr = inputStr;
    this.expected = expected;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"ababgabaabac", "abac", 8},
        {"abacgabaabag", "abac", 0},
        {"abacgabaabac", "a", 0},
        {"ababgabaabag", "ababgabaabag", 0},
        {"ababgabaabag", "g", 4},
        {"ababcabaabac", "g", -1},
        {"ababcabaabag", "g", 11},
        {Common.repeat("ab", 1<<24)+"c", "abababc", 33554426},
    });
  }

    @Override
  protected Runnable getTask() {
    return task;
  }

  @Override
  protected String lastInput() {
    return new StringBuilder()
        .append("Input: ")
        .append(Common.print(inputPattern))
        .append(", ")
        .append(Common.print(inputStr))
        .append("\nExpected: ")
        .append(expected)
        .toString();
  }
}
