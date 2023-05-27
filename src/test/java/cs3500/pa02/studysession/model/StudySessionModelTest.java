package cs3500.pa02.studysession.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa02.studysession.Question;
import cs3500.pa02.studysession.QuestionLabel;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Represents the tests for a study session model
 */
class StudySessionModelTest {
  /**
   * Tests parsing questions given a file path.
   */
  @Test
  void testParseQuestions() {
    String path = "src/test/resources/example.sr";
    StudySessionModel studySessionModel = new StudySessionModel();

    ArrayList<Question> questions;

    try {
      questions = studySessionModel.parseQuestions(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals(2, questions.size());

    Question question1 = questions.get(0);
    Question question2 = questions.get(1);

    assertEquals("What does faster shutter speed reduce?", question1.getQuestion());
    assertEquals("What is ISO?", question2.getQuestion());

    assertEquals("Motion blur", question1.getAnswer());
    assertEquals("Sensor sensitivity", question2.getAnswer());

    assertEquals(QuestionLabel.HARD, question1.getLabel());
    assertEquals(QuestionLabel.EASY, question2.getLabel());
  }

  /**
   * Tests creating a question from a string representation.
   */
  @Test
  void testStringToQuestion() {
    StudySessionModel studySessionModel = new StudySessionModel();
    String exampleQuestion = "This is a question_This is an answer|EASY";
    String exampleQuestion1 = "heads or tails?_tails|HARD";

    Question question = studySessionModel.stringToQuestion(exampleQuestion);
    Question question1 = studySessionModel.stringToQuestion(exampleQuestion1);

    assertEquals("This is a question", question.getQuestion());
    assertEquals("heads or tails?", question1.getQuestion());

    assertEquals("This is an answer", question.getAnswer());
    assertEquals("tails", question1.getAnswer());

    assertEquals(QuestionLabel.EASY, question.getLabel());
    assertEquals(QuestionLabel.HARD, question1.getLabel());
  }

  /**
   * Tests exceptions when converting from a string to a question.
   */
  @Test
  void testStringToQuestionException() {
    String s = "nutrition facts";
    String s1 = "nutrition facts_";
    String s2 = "nutrition facts|";
    StudySessionModel studySessionModel = new StudySessionModel();

    assertThrows(IllegalArgumentException.class,
        () -> studySessionModel.stringToQuestion(s));
    assertThrows(IllegalArgumentException.class,
        () -> studySessionModel.stringToQuestion(s1));
    assertThrows(IllegalArgumentException.class,
        () -> studySessionModel.stringToQuestion(s2));

  }


  /**
   * Tests exceptions when trying to parse questions.
   */
  @Test
  void testParseQuestionException() {
    String path = "src/test/resources/notanSrFile.md";
    StudySessionModel studySessionModel = new StudySessionModel();

    assertThrows(IllegalArgumentException.class,
        () -> studySessionModel.parseQuestions(path));

    String path2 = "src/test/resources/thisDoesntExist.sr";
    assertThrows(IllegalArgumentException.class,
        () -> studySessionModel.parseQuestions(path2));
  }

  /**
   * Tests updating the question.
   */
  @Test
  void testUpdateQuestion() {
    String path = "src/test/resources/example.sr";
    StudySessionModel studySessionModel = new StudySessionModel();

    ArrayList<Question> questions;

    try {
      questions = studySessionModel.parseQuestions(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    studySessionModel.updateQuestion(
        new Question("What does faster shutter speed reduce?", "Motion blur",
            QuestionLabel.HARD), QuestionLabel.EASY);
    studySessionModel.updateQuestion(new Question("What is ISO?", "Sensor sensitivity",
        QuestionLabel.EASY), QuestionLabel.HARD);

    assertEquals(QuestionLabel.EASY, questions.get(0).getLabel());
    assertEquals(QuestionLabel.HARD, questions.get(1).getLabel());
  }
}