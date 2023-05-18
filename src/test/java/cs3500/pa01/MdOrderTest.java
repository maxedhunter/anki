package cs3500.pa01;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents test for ordering markdown files.
 */
class MdOrderTest {
  static final String SAMPLE_INPUTS_DIRECTORY = "src/test/resources/sampleFiles";

  ArrayList<Path> unsorted = new ArrayList<>();
  ArrayList<String> expectedResultString = new ArrayList<>();
  ArrayList<Path> badFiles = new ArrayList<>();


  /**
   * Creates an unsorted array list of file paths
   * to be later sorted.
   */
  @BeforeEach
  void setUp() {
    unsorted.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/vectors.md"));
    unsorted.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/arrays.md"));

    expectedResultString = new ArrayList<>(Arrays.asList("# Java Arrays",
        "- An **array** is a collection of variables of the same type", "\n## Declaring an Array",
        "- General Form: type[] arrayName;", "- only creates a reference",
        "- no array has actually been created yet", "\n## Creating an Array (Instantiation)",
        "- General form:  arrayName = new type[numberOfElements];",
        "- numberOfElements must be a positive Integer.",
        "- Gotcha: Array size is not modifiable once instantiated.", "", "# Vectors",
        "- Vectors act like resizable arrays", "\n## Declaring a vector",
        "- General Form: Vector<type> v = new Vector();",
        "- type needs to be a valid reference type", "\n## Adding an element to a vector",
        "- v.add(object of type);"));

    badFiles.add(Path.of("blahblahblah.txt"));
    badFiles.add(Path.of("blahblahblah2.txt"));
  }

  /**
   * Tests orderedContent getter
   * and exception handling.
   */
  @Test
  void testGetOrderedContent() {
    MdOrder mdOrder = new MdOrder();
    assertThrows(IllegalStateException.class, () -> mdOrder.getOrderedContent());


    mdOrder.orderContent(unsorted, OrderingFlag.FILENAME);

    assertEquals(expectedResultString, mdOrder.getOrderedContent());
  }

  /**
   * Tests ordering by file name.
   */
  @Test
  void testOrderPathFileName() {
    ArrayList<Path> expectedFiles = new ArrayList<>();
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/arrays.md"));
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/vectors.md"));

    MdOrder mdOrder = new MdOrder();

    assertEquals(expectedFiles, mdOrder.orderPath(unsorted, OrderingFlag.FILENAME));
  }

  /**
   * Tests ordering by file creation time.
   */
  @Test
  void testOrderPathFileCreationTime() {
    ArrayList<Path> expectedFiles = new ArrayList<>();
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/arrays.md"));
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/vectors.md"));

    MdOrder mdOrder = new MdOrder();
    assertEquals(expectedFiles, mdOrder.orderPath(expectedFiles, OrderingFlag.CREATED));

    assertThrows(RuntimeException.class, () -> mdOrder.orderPath(badFiles, OrderingFlag.CREATED));
  }

  /**
   * Tests ordering by file last modified time.
   */
  @Test
  void testOrderPathFileLastModifiedTime() {
    ArrayList<Path> expectedFiles = new ArrayList<>();
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/vectors.md"));
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/arrays.md"));

    MdOrder mdOrder = new MdOrder();

    assertEquals(expectedFiles, mdOrder.orderPath(expectedFiles, OrderingFlag.MODIFIED));
    assertThrows(RuntimeException.class, () -> mdOrder.orderPath(badFiles, OrderingFlag.MODIFIED));
  }

  /**
   * Tests returning ordered content.
   */
  @Test
  void testOrderContent() {
    MdOrder mdOrder = new MdOrder();
    ArrayList<Path> expectedFiles = new ArrayList<>();
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/arrays.md"));
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/vectors.md"));

    assertEquals(expectedResultString, mdOrder.orderContent(expectedFiles, OrderingFlag.FILENAME));
  }
}