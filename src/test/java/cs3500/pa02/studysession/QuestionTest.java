package cs3500.pa02.studysession;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}