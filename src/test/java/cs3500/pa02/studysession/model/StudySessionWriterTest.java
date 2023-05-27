package cs3500.pa02.studysession.model;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for a study session writer.
 */
class StudySessionWriterTest {
  static final String SAMPLE_INPUTS_FILE = "src/test/resources/sampleFiles3/output.sr";
  Question q1;
  Question q2;
  Question q3;
  ArrayList<Question> questions;

  @BeforeEach
  void setUp() {
    q1 = new Question("What's 1 + 1?", "2", QuestionLabel.EASY);
    q2 = new Question("What's 1 + 2?", "3", QuestionLabel.HARD);
    q3 = new Question("What's 1 + 3?", "4", QuestionLabel.HARD);

    questions = new ArrayList<>(Arrays.asList(q1, q2, q3));
  }

  /**
   * Tests writing questions to a .sr file.
   */
  @Test
  void testWriteToFile() {
    StudySessionWriter studySessionWriter = new StudySessionWriter();
    List<String> actualContent;

    ArrayList<String> expectedContent = new ArrayList<>(
        Arrays.asList("What's 1 + 1?_2|EASY", "What's 1 + 2?_3|HARD", "What's 1 + 3?_4|HARD"));

    try {
      studySessionWriter.writeToFile(questions, Path.of(SAMPLE_INPUTS_FILE));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      actualContent = Files.readAllLines(Path.of(SAMPLE_INPUTS_FILE));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ArrayList<String> actualContentArrayList = new ArrayList<>(actualContent);

    assertEquals(expectedContent, actualContentArrayList);
  }

  /**
   * Tests exceptions when writing to a file.
   */
  @Test
  void testWriteToFileException() {
    StudySessionWriter studySessionWriter = new StudySessionWriter();

    assertThrows(IllegalArgumentException.class,
        () -> studySessionWriter.writeToFile(questions,
            Path.of("sampleFiles3/thisFileDoesExist.txt")));
    assertThrows(IllegalArgumentException.class,
        () -> studySessionWriter.writeToFile(questions,
            Path.of("sampleFiles3/thisFileDoesntExist.txt")));
  }
}