import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class DiffTest extends AbstractTest {

  String input1;
  String input2;
  String expected;
  Runnable task = new Runnable() {
    @Override
    public void run() {
      String actual = new Diff().findDiff(input1, input2);
      if (!expected.equals(actual)) {
        Common.assertEquals(error(String.valueOf(actual)), expected, actual);
      }
    }
  };

  public DiffTest(String input1, String input2, String expected) {
    super("Diff");
    this.input1 = input1;
    this.input2 = input2;
    this.expected = expected;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"abc", "cab", "--\n++"},
        {"abc", "abcd", "--\n++3;"},
        {"abc", "aaa", "--1;2;\n++1;2;"},
        {"Sunday", "Monday", "--0;1;\n++0;1;"},
    });
  }

  @Override
  protected Runnable getTask() {
    return task;
  }

  @Override
  protected String lastInput() {
    return new StringBuilder()
        .append("Input: \"")
        .append(Common.print(input1))
        .append("\", \"")
        .append(Common.print(input2))
        .append("\"\nExpected: ")
        .append(Common.print(expected))
        .toString();
  }
}
