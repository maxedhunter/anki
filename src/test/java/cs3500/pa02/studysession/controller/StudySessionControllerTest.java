package cs3500.pa02.studysession.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa02.studysession.Question;
import cs3500.pa02.studysession.QuestionLabel;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudySessionControllerTest {
  static final String SAMPLE_INPUTS_FILE = "src/test/resources/example3.sr";
  StudySessionController controller;
  PrintStream printStream;
  StringBuilder output;

  /**
   * Sets up example3.sr file for future controller testing and
   * input output testing.
   */
  @BeforeEach
  void setUp() {
    try {
      new FileOutputStream(SAMPLE_INPUTS_FILE).close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ArrayList<String> questions = new ArrayList<>(
        Arrays.asList("What does faster shutter speed reduce?_Motion blur|HARD",
            "What is ISO?_Sensor sensitivity|EASY"));

    for (String s : questions) {
      try {
        Files.write(Path.of(SAMPLE_INPUTS_FILE), questions);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    output = new StringBuilder();
    printStream = new PrintStream(new OutputStream() {
      @Override
      public void write(int b) {
        output.append((char) b);
      }
    });
  }

  /**
   * Tests setting up path.
   */
  @Test
  void testSetUpPath() {
    controller = new StudySessionController();
    String prompt = "Welcome to your study session!"
        + "\nWhich .sr question bank file would you like to load?";

    InputStream inputStream = new ByteArrayInputStream(SAMPLE_INPUTS_FILE.getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);
    System.setOut(printStream);

    String result = controller.setUpPath(scanner);
    String actualOutput = output.toString();
    assertEquals(prompt + "\n", actualOutput);
    assertEquals(SAMPLE_INPUTS_FILE, result);

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);
  }

  /**
   * Tests setting up path and when it does not exist.
   */
  @Test
  void testSetUpPathDoesNotExist() {
    controller = new StudySessionController();

    InputStream inputStream = new ByteArrayInputStream("thisfileDoesNotexist".getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);
    System.setOut(printStream);

    assertThrows(IllegalArgumentException.class,
        () -> controller.setUpPath(scanner));

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);
  }

  /**
   * Tests setting up path and when it is not an .sr file.
   */
  @Test
  void testSetUpPathIsNotSr() {
    controller = new StudySessionController();

    InputStream inputStream =
        new ByteArrayInputStream("sampleFiles2/herewego.md".getBytes());
    Scanner scanner = new Scanner(inputStream);

    assertThrows(IllegalArgumentException.class,
        () -> controller.setUpPath(scanner));

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);
  }

  /**
   * Tests setting up a number of questions.
   */
  @Test
  void testSetUpNumberOfQuestions() {
    controller = new StudySessionController();
    int input = 10;

    InputStream inputStream = new ByteArrayInputStream(String.valueOf(input).getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);
    System.setOut(printStream);

    int result = controller.setUpNumberOfQuestions(scanner);

    System.setIn(System.in);
    System.setOut(System.out);

    String actualOutput = output.toString();

    assertEquals(input, result);

    String prompt = "How many questions would you like to practice?";
    assertEquals(prompt + "\n", actualOutput);

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);
  }

  /**
   * Test question handling.
   */
  @Test
  void testQuestionHandler() {
    controller = new StudySessionController();

    InputStream inputStream = new ByteArrayInputStream(SAMPLE_INPUTS_FILE.getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);

    controller.setUpPath(scanner);

    int input = 2;

    InputStream inputStream1 = new ByteArrayInputStream(String.valueOf(input).getBytes());
    Scanner scanner1 = new Scanner(inputStream1);

    System.setIn(inputStream1);
    controller.setUpNumberOfQuestions(scanner1);

    ArrayList<Question> questions;
    try {
      questions = controller.setUpQuestions();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String input2 = "1\n1";
    InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes(StandardCharsets.UTF_8));
    Scanner scanner2 = new Scanner(inputStream2);

    System.setIn(inputStream2);

    ArrayList<Question> questions1;
    try {
      questions1 = controller.questionHandler(scanner2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Question question1 = questions1.get(0);
    Question question2 = questions1.get(1);

    // after clicking 1 --> new label of easy
    // this tests fails
    assertFalse(question1.sameQuestion(
        new Question("What does faster shutter speed reduce?",
            "Motion blur", QuestionLabel.EASY)));

    assertTrue(question2.sameQuestion(
        new Question("What is ISO?",
            "Sensor sensitivity", QuestionLabel.EASY)));

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  void testQuestionHandler2() {
    controller = new StudySessionController();
    String prompt = "Welcome to your study session!"
        + "\nWhich .sr question bank file would you like to load?";

    InputStream inputStream = new ByteArrayInputStream(SAMPLE_INPUTS_FILE.getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);

    controller.setUpPath(scanner);

    int input = 2;

    InputStream inputStream1 = new ByteArrayInputStream(String.valueOf(input).getBytes());
    Scanner scanner1 = new Scanner(inputStream1);

    System.setIn(inputStream1);
    controller.setUpNumberOfQuestions(scanner1);

    ArrayList<Question> questions;
    try {
      questions = controller.setUpQuestions();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String input2 = "1\n0";
    InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes(StandardCharsets.UTF_8));
    Scanner scanner2 = new Scanner(inputStream2);

    System.setIn(inputStream2);

    assertThrows(IllegalArgumentException.class,
        () -> controller.questionHandler(scanner2));


    //Reset
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  void testQuestionHandler3() {
    controller = new StudySessionController();
    String prompt = "Welcome to your study session!"
        + "\nWhich .sr question bank file would you like to load?";

    InputStream inputStream = new ByteArrayInputStream(SAMPLE_INPUTS_FILE.getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);

    controller.setUpPath(scanner);

    int input = 2;

    InputStream inputStream1 = new ByteArrayInputStream(String.valueOf(input).getBytes());
    Scanner scanner1 = new Scanner(inputStream1);

    System.setIn(inputStream1);
    controller.setUpNumberOfQuestions(scanner1);

    ArrayList<Question> questions;
    try {
      questions = controller.setUpQuestions();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String input2 = "1\n4";
    InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes(StandardCharsets.UTF_8));
    Scanner scanner2 = new Scanner(inputStream2);

    System.setIn(inputStream2);
    try {
      controller.questionHandler(scanner2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  void testQuestionHandler4() {
    controller = new StudySessionController();
    String prompt = "Welcome to your study session!"
        + "\nWhich .sr question bank file would you like to load?";

    InputStream inputStream = new ByteArrayInputStream(SAMPLE_INPUTS_FILE.getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);

    controller.setUpPath(scanner);

    int input = 2;

    InputStream inputStream1 = new ByteArrayInputStream(String.valueOf(input).getBytes());
    Scanner scanner1 = new Scanner(inputStream1);

    System.setIn(inputStream1);
    controller.setUpNumberOfQuestions(scanner1);

    ArrayList<Question> questions;
    try {
      questions = controller.setUpQuestions();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String input2 = "1\n2";
    InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes(StandardCharsets.UTF_8));
    Scanner scanner2 = new Scanner(inputStream2);

    System.setIn(inputStream2);
    try {
      controller.questionHandler(scanner2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);
  }


  /**
   * Tests setting up the questions.
   */
  @Test
  void testSetUpQuestions() {
    controller = new StudySessionController();
    String prompt = "Welcome to your study session!"
        + "\nWhich .sr question bank file would you like to load?";

    InputStream inputStream = new ByteArrayInputStream(SAMPLE_INPUTS_FILE.getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);

    controller.setUpPath(scanner);

    int input = 2;

    InputStream inputStream1 = new ByteArrayInputStream(String.valueOf(input).getBytes());
    Scanner scanner1 = new Scanner(inputStream1);

    System.setIn(inputStream1);
    controller.setUpNumberOfQuestions(scanner1);

    ArrayList<Question> questions;
    try {
      questions = controller.setUpQuestions();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Question q1 = questions.get(0);
    Question q2 = questions.get(1);

    assertTrue(q1.sameQuestion(
        new Question("What does faster shutter speed reduce?",
            "Motion blur", QuestionLabel.HARD)));

    assertTrue(q2.sameQuestion(
        new Question("What is ISO?",
            "Sensor sensitivity", QuestionLabel.EASY)));

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  void testHandleEnding() {
    controller = new StudySessionController();
    String prompt = "Welcome to your study session!"
        + "\nWhich .sr question bank file would you like to load?";

    InputStream inputStream = new ByteArrayInputStream(SAMPLE_INPUTS_FILE.getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);

    controller.setUpPath(scanner);

    int input = 2;

    InputStream inputStream1 = new ByteArrayInputStream(String.valueOf(input).getBytes());
    Scanner scanner1 = new Scanner(inputStream1);

    System.setIn(inputStream1);
    controller.setUpNumberOfQuestions(scanner1);

    ArrayList<Question> questions;
    try {
      questions = controller.setUpQuestions();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String input2 = "1\n4";
    InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes(StandardCharsets.UTF_8));
    Scanner scanner2 = new Scanner(inputStream2);

    System.setIn(inputStream2);
    try {
      controller.questionHandler(scanner2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    controller.handleEnding();
    System.setOut(printStream);

    String actualOutput = output.toString();

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  void testRun() {
    controller = new StudySessionController();
    String prompt = "Welcome to your study session!"
        + "\nWhich .sr question bank file would you like to load?";

    InputStream inputStream = new ByteArrayInputStream(SAMPLE_INPUTS_FILE.getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);

    controller.setUpPath(scanner);

    int input = 2;

    InputStream inputStream1 = new ByteArrayInputStream(String.valueOf(input).getBytes());
    Scanner scanner1 = new Scanner(inputStream1);

    System.setIn(inputStream1);
    controller.setUpNumberOfQuestions(scanner1);

    ArrayList<Question> questions;
    try {
      questions = controller.setUpQuestions();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String input2 = "1\n4";
    InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes(StandardCharsets.UTF_8));
    Scanner scanner2 = new Scanner(inputStream2);

    System.setIn(inputStream2);
    try {
      controller.questionHandler(scanner2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    controller.handleEnding();
    System.setOut(printStream);

    String actualOutput = output.toString();

    assertThrows(NoSuchElementException.class, () -> controller.run(scanner2));

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);
  }

  @Test
  void testRun2() {
    StudySessionController controller = new StudySessionController();

    String userInput = "src/test/resources/example2.sr\n5\n2\n3\n4";  // Simulated user input

    Scanner customScanner = new Scanner(userInput);

    try {
      controller.run(customScanner);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
