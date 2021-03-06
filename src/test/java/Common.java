import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class Common {

  public static final String START = "[codegym-test]";
  public static final String END = "[/codegym-test]";

  public static String error(String msg) {
    return new StringBuilder().append(START).append(msg).append(END).toString();
  }

  public static boolean hasError(String msg) {
    return msg.contains(START);
  }

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

  public static String printMatrix(int[][] m) {
    return printMatrix(m, new StringBuilder()).toString();
  }

  public static StringBuilder printMatrix(int[][] m, StringBuilder buf) {
    buf.append('[');
    for (int[] row : m) {
      printArray(row, buf);
    }
    return buf.append(']');
  }

  public static StringBuilder printChars(char[] input, StringBuilder buf) {
    buf.append('[');
    int minLen = Math.min(100, input.length);
    for (int i = 0; i < minLen; i++) {
      buf.append('\'').append(input[i]).append("', ");
    }
    if (minLen < input.length) {
      buf.append("...");
    }
    return buf.append(']');
  }

  public static String printBits(byte[] actual) {
    return null;
  }

  public static String toBinaryString(int num) {
    StringBuilder buf = new StringBuilder();
    for (int i = 31; i >= 0; i--) {
      char c = ((num & (1 << i)) != 0) ? '1' : '0';
      buf.append(c);
    }
    return buf.toString();
  }

  public static void assertEquals(String msg, int o1, int o2) {
    Assert.assertEquals(msg, o1, o2);
  }

  public static void assertEquals(String msg, int[] expected, int[] actual) {
    Assert.assertArrayEquals(msg, expected, actual);
  }

  public static void assertEquals(String msg, Object o1, Object o2) {
    Assert.assertEquals(msg, o1, o2);
  }

  public static void assertEquals(String msg, boolean o1, boolean o2) {
    Assert.assertEquals(msg, o1, o2);
  }

  public static String print(String input) {
    if (input != null && input.length() > 100) {
      return String.format("[length:%d] ", input.length()) +
          input.substring(0, 97).replace("\n", "\\n")
          + "...";
    }
    return input.replace("\n", "\\n");
  }

  public static String generateFrom(String abc, int size) {
    StringBuilder buf = new StringBuilder();
    boolean[] used = new boolean[abc.length()];
    for (int i = 0; i < size; i++) {
      int index = (int) (Math.random() * abc.length());
      used[index] = true;
      buf.append(abc.charAt(index));
    }
    for (int i = 0; i < used.length; i++) {
      if (!used[i]) {
        buf.append(abc.charAt(i));
      }
    }
    return buf.toString();
  }

  public static boolean different(int actual, int expected) {
    return actual != expected;
  }

  public static boolean different(Object actual, Object expected) {
    if (actual == null && expected == null) return false;
    if (actual == null || expected == null) return true;
    return !actual.equals(expected);
  }

  public static int[] repeatedRandomNumbers(int repeat, int size, int unique) {
    int[] res = new int[size];
    int i = 0;
    int salt = (int) (Math.random() * (size / repeat));
    for (int j = 0; j < repeat; j++) {
      for (int k = 0; k < size / repeat; k++) {
        res[i++] = k + salt;
      }
    }
    res[res.length - 1] = unique;
    return res;
  }

  public static String generateSentence(int words, int minLength) {
    StringBuilder res = new StringBuilder();
    int wLen = Math.max(minLength / (words + 1), 1);
    String delimiters = " .,?!@#$%^&*()_+=-~";
    System.out.println(wLen);
    System.out.println(minLength);

    for (int i = 0; i < words; i++) {
      int len = Math.max(1, (int) (Math.random() * wLen));

      for (int j = 0; j < len; j++) {
        char c = (char) ('a' + (i + j) % 26);
        res.append(c);
      }
      res.append(delimiters.charAt(i % delimiters.length()));
    }
    for (int i = res.length(); i < minLength; i++) {
      res.append(delimiters.charAt(i % delimiters.length()));
    }
    return res.toString();
  }

  public static void fail(String message) {
    Assert.fail(message);
  }

  public static void assertCondition(boolean condition, String message) {
    if (!condition) fail(message);
  }

  public static <T> List<T> list(T... elements) {
    List<T> res = new ArrayList<>(elements.length);
    for (T element : elements) {
      res.add(element);
    }
    return res;
  }

  public static String repeat(String pattern, int times) {
    StringBuilder buf = new StringBuilder(pattern.length()*times);
    for (int i = 0; i < times; i++) {
      buf.append(pattern);
    }
    return buf.toString();
  }
}
