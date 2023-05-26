package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa02.studysession.Question;
import cs3500.pa02.studysession.QuestionLabel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for the markdown file writer
 * with added functionally for questions and .sr files.
 */
class MdWriterTest {

  static final String SAMPLE_INPUTS_DIRECTORY = "src/test/resources/sampleFiles2";

  /**
   * Tests writing to a .md file.
   */
  @Test
  void testWriteToFile() {
    List<String> actualContent;

    MdWriter mdWriter1 = new MdWriter(SAMPLE_INPUTS_DIRECTORY + "/testingOutput.md");

    ArrayList<String> expectedContent = new ArrayList<>(Arrays.asList("one", "two", "three"));

    mdWriter1.writeToFile(expectedContent);

    try {
      actualContent = Files.readAllLines(
          Path.of(SAMPLE_INPUTS_DIRECTORY + "/testingOutput.md"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ArrayList<String> actualContentArrayList = new ArrayList<>(actualContent);

    assertEquals(expectedContent, actualContentArrayList);
  }

  /**
   * Tests exceptions when writing to a .md file.
   */
  @Test
  void testWriteToFileException() {
    MdWriter mdWriter = new MdWriter("badfile.txt");
    assertThrows(IllegalArgumentException.class,
        () -> mdWriter.writeToFile(new ArrayList<>()));


    MdWriter mdWriter1 = new MdWriter("path/this/doesnt/exist/beepboop.md");
    assertThrows(RuntimeException.class,
        () -> mdWriter1.writeToFile(new ArrayList<>()));
  }

  /**
   * Tests writing to a .sr file.
   */
  @Test
  void testWriteToSrFile() {
    List<String> actualContent;

    MdWriter mdWriter1 = new MdWriter(SAMPLE_INPUTS_DIRECTORY + "/testingOutput2.md");

    Question q1 = new Question("What's 1 + 1?", "2", QuestionLabel.EASY);
    Question q2 = new Question("What's 1 + 2?", "3", QuestionLabel.HARD);
    Question q3 = new Question("What's 1 + 3?", "4", QuestionLabel.HARD);

    ArrayList<Question> questions = new ArrayList<>(Arrays.asList(q1, q2, q3));

    ArrayList<String> expectedContent = new ArrayList<>(
        Arrays.asList("What's 1 + 1?|2|EASY", "What's 1 + 2?|3|HARD", "What's 1 + 3?|4|HARD"));

    mdWriter1.writeToFileQuestions(questions);

    try {
      actualContent = Files.readAllLines(
          Path.of(SAMPLE_INPUTS_DIRECTORY + "/testingOutput2.sr"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ArrayList<String> actualContentArrayList = new ArrayList<>(actualContent);

    assertEquals(expectedContent, actualContentArrayList);
  }


  /**
   * Tests exceptions when writing to a .sr file.
   */
  @Test
  void testWriteToSrFileException() {
    MdWriter mdWriter = new MdWriter("badfile.txt");
    assertThrows(IllegalArgumentException.class,
        () -> mdWriter.writeToFileQuestions(new ArrayList<>()));

    MdWriter mdWriter1 = new MdWriter("path/this/doesnt/exist/beepboop.md");
    assertThrows(RuntimeException.class,
        () -> mdWriter1.writeToFileQuestions(new ArrayList<>()));
  }
}