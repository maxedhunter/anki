package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Represents tests for Question class.
 */
class QuestionTest {

  /**
   * Tests updating the label.
   */
  @Test
  void testUpdateLabel() {
    Question example = new Question("How's the weather", "Bad", QuestionLabel.EASY);
    example.updateLabel(QuestionLabel.HARD);
    assertEquals(QuestionLabel.HARD, example.getLabel());
  }

  /**
   * Tests returning the question.
   */
  @Test
  void testGetQuestion() {
    Question example = new Question("Testing?", "yup", QuestionLabel.HARD);
    assertEquals("Testing?", example.getQuestion());
  }

  /**
   * Tests returning the answer.
   */
  @Test
  void testGetAnswer() {
    Question example = new Question("Testing?", "yup", QuestionLabel.HARD);
    assertEquals("yup", example.getAnswer());
  }

  /**
   * Tests returning the label.
   */
  @Test
  void testGetLabel() {
    Question example = new Question("Testing?", "yup", QuestionLabel.HARD);
    assertEquals(QuestionLabel.HARD, example.getLabel());

    Question example2 = new Question("hi?", "hello");
    assertEquals(QuestionLabel.HARD, example2.getLabel());
  }

  /**
   * Tests a boolean function checking if two questions are the same.
   */
  @Test
  void testSameQuestion() {
    Question example = new Question("1", "2", QuestionLabel.HARD);
    Question example1 = new Question("1", "2", QuestionLabel.HARD);
    Question example2 = new Question("1", "2", QuestionLabel.EASY);

    assertTrue(example.sameQuestion(example1));
    assertFalse(example1.sameQuestion(example2));

    Question example3 = new Question("2", "2", QuestionLabel.EASY);
    Question example4 = new Question("1", "1", QuestionLabel.EASY);
    assertFalse(example.sameQuestion(example3));
    assertFalse(example.sameQuestion(example4));
  }
}