package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents Driver tests.
 */
class DriverTest {
  Driver driver = new Driver();

  PrintStream printStream;
  StringBuilder output;

  /**
   * Sets up input output testing.
   */
  @BeforeEach
  void setUp() {
    output = new StringBuilder();
    printStream = new PrintStream(new OutputStream() {
      @Override
      public void write(int b) {
        output.append((char) b);
      }
    });
  }

  /**
   * Tests the main class.
   */
  @Test
  void testMain2() {
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(new String[] {"1"}));

    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(new String[] {"1", "2", "3", "4", "5"}));

  }

  @Test
  void testMain() {
    String input = "\n";
    System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

    assertThrows(NoSuchElementException.class,
        () -> Driver.main(new String[] {}));
  }


}