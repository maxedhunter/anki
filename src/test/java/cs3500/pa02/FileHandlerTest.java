package cs3500.pa02;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa02.studysession.Question;
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
        "- General Form: type[] arrayName;", "- only creates a reference",
        "- no array has actually been created yet", "\n## Creating an Array (Instantiation)",
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

  /**
   * Tests a question format where
   * [[, :::, and ]] are all on the same line
   */
  @Test
  void testQuestionHandlerFormatOne() {
    FileHandler fileHandler1 = new FileHandler();
    fileHandler1.createContent(Paths.get(SAMPLE_INPUTS_DIRECTORY + "/questions.md"));

    ArrayList<Question> questions = fileHandler1.getQuestions();

    assertEquals(8, questions.size());
    String question = questions.get(0).getQuestion();
    String answer = questions.get(0).getAnswer();

    assertEquals("Which country is known as the Land of Fire and Ice?", question);
    assertEquals("Iceland.", answer);

    String question1 = questions.get(1).getQuestion();
    String answer1 = questions.get(1).getAnswer();

    assertEquals("What is the longest river in Europe?", question1);
    assertEquals("The longest river is the Volga River.", answer1);
  }

  /**
   * Tests a question format where [[ and :::
   * are on the same line and ]] is on another.
   */
  @Test
  void testQuestionHandlerFormatTwo() {
    FileHandler fileHandler1 = new FileHandler();
    fileHandler1.createContent(Paths.get(SAMPLE_INPUTS_DIRECTORY + "/questions.md"));

    ArrayList<Question> questions = fileHandler1.getQuestions();
    assertEquals(8, questions.size());

    String question = questions.get(2).getQuestion();
    String answer = questions.get(2).getAnswer();
    assertEquals("What is the largest island in the world?", question);
    assertEquals("The largest island is Greenland.", answer);

    String question1 = questions.get(3).getQuestion();
    String answer1 = questions.get(3).getAnswer();

    assertEquals("Favorite Color?", question1);
    assertEquals("Green", answer1);
  }

  /**
   * Tests a question format where
   * [[ is on one line and ::: and ]] is on another.
   */
  @Test
  void testQuestionHandlerFormatThree() {
    FileHandler fileHandler1 = new FileHandler();
    fileHandler1.createContent(Paths.get(SAMPLE_INPUTS_DIRECTORY + "/questions.md"));

    ArrayList<Question> questions = fileHandler1.getQuestions();
    assertEquals(8, questions.size());

    String question = questions.get(4).getQuestion();
    String answer = questions.get(4).getAnswer();
    assertEquals("What color is the sky?", question);
    assertEquals("Blue", answer);

    String question1 = questions.get(5).getQuestion();
    String answer1 = questions.get(5).getAnswer();

    assertEquals("Can you tell me the time?", question1);
    assertEquals("3:45", answer1);
  }

  /**
   * Tests a question format where
   * [[, :::, and ]] are all on different lines.
   */
  @Test
  void testQuestionHandlerFormatFour() {
    FileHandler fileHandler1 = new FileHandler();
    fileHandler1.createContent(Paths.get(SAMPLE_INPUTS_DIRECTORY + "/questions.md"));

    ArrayList<Question> questions = fileHandler1.getQuestions();
    assertEquals(8, questions.size());

    String question = questions.get(6).getQuestion();
    String answer = questions.get(6).getAnswer();
    assertEquals("tea?", question);
    assertEquals("green tea", answer);

    String question1 = questions.get(7).getQuestion();
    String answer1 = questions.get(7).getAnswer();

    assertEquals("coffee?", question1);
    assertEquals("black coffee", answer1);
  }
}