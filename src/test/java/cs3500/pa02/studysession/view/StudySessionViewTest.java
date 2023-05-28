package cs3500.pa02.studysession.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa02.studysession.Question;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents the tests for the view class
 * of a study session.
 */
class StudySessionViewTest {
  StudySessionView studySessionView = new StudySessionView();
  PrintStream printStream;
  StringBuilder output;

  /**
   * Sets up input / output testing.
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
   * Tests the input and output of getting user path.
   */
  @Test
  void testGetUserSrFilePath() {
    String prompt = "Enter the file path: ";
    String input = "/exampleFile.sr";

    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);
    System.setOut(printStream);

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);

    String result = studySessionView.getUserSrFilePath(prompt, scanner);

    String actualOutput = output.toString();
    assertEquals(prompt + "\n", actualOutput);
    assertEquals(input, result);
  }

  /**
   * Tests the input and output when asking the user for a number of questions.
   */
  @Test
  void testGetNumberOfQuestions() {
    String prompt = "Enter the number of questions you would like to study: ";
    int input = 10;

    InputStream inputStream = new ByteArrayInputStream(String.valueOf(input).getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);
    System.setOut(printStream);

    int result = studySessionView.getNumberOfQuestions(prompt, scanner);

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);

    String actualOutput = output.toString();

    assertEquals(input, result);
    assertEquals(prompt + "\n", actualOutput);
  }

  /**
   * Tests displaying a question
   */
  @Test
  void testDisplayQuestion() {
    Question question = new Question("What's up", "the sky");

    System.setOut(printStream);
    studySessionView.displayQuestion(question);
    String actualOutput = output.toString();
    assertEquals("What's up\n", actualOutput);

    // Reset
    System.setOut(System.out);
  }

  /**
   * Tests the input and output when asking the user for an integer response.
   */
  @Test
  void testGetUserIntegerResponse() {
    String prompt = "Pick one of the following:\n(1) Easy\n(2) Hard\n(3) Show answer";
    int input = 3;

    InputStream inputStream = new ByteArrayInputStream(String.valueOf(input).getBytes());
    Scanner scanner = new Scanner(inputStream);

    System.setIn(inputStream);
    System.setOut(printStream);

    int result = studySessionView.getUserIntegerResponse(prompt, scanner);

    //Reset
    System.setIn(System.in);
    System.setOut(System.out);

    String actualOutput = output.toString();

    assertEquals(input, result);
    assertEquals(prompt + "\n", actualOutput);
  }

  /**
   * Tests displaying an answer.
   */
  @Test
  void testDisplayAnswer() {
    Question question = new Question("What's up", "the sky");

    System.setOut(printStream);
    studySessionView.displayAnswer(question);
    String actualOutput = output.toString();
    assertEquals("the sky\n", actualOutput);

    // Reset
    System.setOut(System.out);
  }

  /**
   * Tests displaying the final screen
   */
  @Test
  void testDisplayFinalScreen() {
    ArrayList<String> stats =
        new ArrayList<>(Arrays.asList("Studied 3 cards", "3 cards went from hard to easy"));

    System.setOut(printStream);
    studySessionView.displayFinalScreen(stats);
    String actualOutput = output.toString();
    assertEquals("Studied 3 cards\n3 cards went from hard to easy\n", actualOutput);

    // Reset
    System.setOut(System.out);
  }
}