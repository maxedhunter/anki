package cs3500.pa02.studysession;

/**
 * Represents a question.
 */
public class Question {
  String question;
  String answer;
  QuestionLabel difficulty;

  /**
   * Initializes the question, answer, and
   * difficulty of a question.
   *
   * @param question   a question
   * @param answer     the answer to the question
   * @param difficulty how hard a question is
   */
  public Question(String question, String answer, QuestionLabel difficulty) {
    this.question = question;
    this.answer = answer;
    this.difficulty = difficulty;
  }

  /**
   * A convenience constructor that creates a question
   * and initializes the difficulty to hard.
   *
   * @param question a question
   * @param answer   the answer to the question
   */
  public Question(String question, String answer) {
    this.question = question;
    this.answer = answer;
    this.difficulty = QuestionLabel.HARD;
  }

  /**
   * Updates the difficulty label of a question.
   *
   * @param difficulty how hard a question is
   */
  public void updateLabel(QuestionLabel difficulty) {
    this.difficulty = difficulty;
  }

  /**
   * Returns the question.
   *
   * @return question
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Returns the answer.
   *
   * @return answer
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * Returns the label.
   *
   * @return difficulty label
   */
  public QuestionLabel getLabel() {
    return difficulty;
  }

  /**
   * Returns the string in the form of a question
   *
   * @return string format of a question
   */
  public String toString() {
    return question + "_" + answer + "|" + difficulty.toString();
  }

  /**
   * Compares this question with another one,
   * returns true if they are the same question.
   *
   * @param question question to be compared
   * @return whether the two questions are the same
   */
  public boolean sameQuestion(Question question) {
    return this.question.equals(question.getQuestion())
        && this.answer.equals(question.getAnswer()) && this.difficulty.equals(question.getLabel());
  }
}
