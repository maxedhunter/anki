package cs3500.pa02;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa02.studysession.Question;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents test for ordering markdown files.
 */
class MdOrderFormatterTest {
  static final String SAMPLE_INPUTS_DIRECTORY = "src/test/resources/sampleFiles";
  static final String SAMPLE_INPUTS_DIRECTORY2 = "src/test/resources/sampleFiles/questionFiles";

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
    MdOrderFormatter mdOrderFormatter = new MdOrderFormatter();
    assertThrows(IllegalStateException.class, () -> mdOrderFormatter.getOrderedContent());


    mdOrderFormatter.orderContent(unsorted, OrderingFlag.FILENAME);

    assertEquals(expectedResultString, mdOrderFormatter.getOrderedContent());
  }

  /**
   * Tests ordering by file name.
   */
  @Test
  void testOrderPathFileName() {
    ArrayList<Path> expectedFiles = new ArrayList<>();
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/arrays.md"));
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/vectors.md"));

    MdOrderFormatter mdOrderFormatter = new MdOrderFormatter();

    assertEquals(expectedFiles, mdOrderFormatter.orderPath(unsorted, OrderingFlag.FILENAME));
  }

  /**
   * Tests ordering by file creation time.
   */
  @Test
  void testOrderPathFileCreationTime() {
    ArrayList<Path> expectedFiles = new ArrayList<>();
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/arrays.md"));
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/vectors.md"));

    MdOrderFormatter mdOrderFormatter = new MdOrderFormatter();
    assertEquals(expectedFiles, mdOrderFormatter.orderPath(expectedFiles, OrderingFlag.CREATED));

    assertThrows(RuntimeException.class,
        () -> mdOrderFormatter.orderPath(badFiles, OrderingFlag.CREATED));
  }

  /**
   * Tests ordering by file last modified time.
   */
  @Test
  void testOrderPathFileLastModifiedTime() {
    ArrayList<Path> expectedFiles = new ArrayList<>();
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/vectors.md"));
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/arrays.md"));

    MdOrderFormatter mdOrderFormatter = new MdOrderFormatter();

    assertEquals(expectedFiles, mdOrderFormatter.orderPath(expectedFiles, OrderingFlag.MODIFIED));
    assertThrows(RuntimeException.class,
        () -> mdOrderFormatter.orderPath(badFiles, OrderingFlag.MODIFIED));
  }

  /**
   * Tests returning ordered content.
   */
  @Test
  void testOrderContent() {
    MdOrderFormatter mdOrderFormatter = new MdOrderFormatter();
    ArrayList<Path> expectedFiles = new ArrayList<>();
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/arrays.md"));
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/vectors.md"));

    assertEquals(expectedResultString,
        mdOrderFormatter.orderContent(expectedFiles, OrderingFlag.FILENAME));
  }

  /**
   * Tests getting questions from an arraylist of paths.
   */
  @Test
  void testGetQuestions() {
    MdOrderFormatter mdOrderFormatter = new MdOrderFormatter();
    ArrayList<Path> questionFiles = new ArrayList<>();
    questionFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY2 + "/questions1.md"));
    questionFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY2 + "/questions2.md"));

    ArrayList<Question> actualQuestions = mdOrderFormatter.getQuestions(questionFiles);

    assertEquals(3, actualQuestions.size());

    String question = actualQuestions.get(0).getQuestion();
    String answer = actualQuestions.get(0).getAnswer();

    assertEquals("here's a question", question);
    assertEquals("yup", answer);

    String question1 = actualQuestions.get(1).getQuestion();
    String answer1 = actualQuestions.get(1).getAnswer();

    assertEquals("here's a second question", question1);
    assertEquals("thanks", answer1);

    String question2 = actualQuestions.get(2).getQuestion();
    String answer2 = actualQuestions.get(2).getAnswer();

    assertEquals("a third?", question2);
    assertEquals("no way", answer2);
  }
}