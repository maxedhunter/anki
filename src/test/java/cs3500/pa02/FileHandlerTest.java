package cs3500.pa02;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for the file handler
 */
class FileHandlerTest {
  FileHandler fileHandler;
  static final String SAMPLE_INPUTS_DIRECTORY = "src/test/resources/sampleFiles";

  /**
   * Initializes the file handler.
   */
  @BeforeEach
  void setup() {
    fileHandler = new FileHandler();
  }

  /**
   * Tests summarizing the entire file
   * when provided absolute and relative paths.
   */
  @Test
  void testFileHandler() {
    ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList("# Java Arrays",
        "- An **array** is a collection of variables of the same type", "\n## Declaring an Array",
        "- General Form: type[] arrayName;",
        "- only creates a reference", "- no array has actually been created yet",
        "\n## Creating an Array (Instantiation)",
        "- General form:  arrayName = new type[numberOfElements];",
        "- numberOfElements must be a positive Integer.",
        "- Gotcha: Array size is not modifiable once instantiated."));

    ArrayList<String> expectedResult2 = new ArrayList<>(
        Arrays.asList("# Vectors", "- Vectors act like resizable arrays", "\n## Declaring a vector",
            "- General Form: Vector<type> v = new Vector();",
            "- type needs to be a valid reference type", "\n## Adding an element to a vector",
            "- v.add(object of type);"));

    assertEquals(expectedResult,
        fileHandler.createContent(Paths.get(SAMPLE_INPUTS_DIRECTORY + "/arrays.md")));

    assertEquals(expectedResult2,
        fileHandler.createContent(Paths.get(SAMPLE_INPUTS_DIRECTORY + "/vectors.md")));

    assertThrows(IllegalArgumentException.class, () -> fileHandler.createContent(
        Paths.get(SAMPLE_INPUTS_DIRECTORY + "/thisfiledoesntexist.md")));
  }
}