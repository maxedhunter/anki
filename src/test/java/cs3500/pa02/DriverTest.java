package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Represents Driver tests.
 */
class DriverTest {

  /**
   * Tests exceptions in the main class.
   */
  @Test
  public void testMain() {
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(new String[] {}));
  }
}