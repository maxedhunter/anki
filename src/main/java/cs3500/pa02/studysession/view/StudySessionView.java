package cs3500.pa02.studysession.view;

import cs3500.pa02.studysession.Question;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the view of a study session.
 */
public class StudySessionView {

  /**
   * Prompts the user to provide a file path.
   *
   * @param prompt prompts the user to type in a file path
   * @return the String version of a path
   */
  public String getUserSrFilePath(String prompt, Scanner scanner) {
    System.out.println(prompt);
    return scanner.next();
  }

  /**
   * Prompts the user to enter how many questions they
   * would like to complete during this study session.
   *
   * @param prompt prompts the user to provide a number of questions
   * @return how many questions the user would like to study
   */
  public int getNumberOfQuestions(String prompt, Scanner scanner) {
    System.out.println(prompt);
    return scanner.nextInt();
  }

  /**
   * Prints out the question to the user.
   *
   * @param question question of a question/answer pair
   */
  public void displayQuestion(Question question) {
    System.out.println(question.getQuestion());
  }

  /**
   * Gets the user response.
   *
   * @param prompt a string indicating what the user should type
   * @return the users choice
   */
  public int getUserIntegerResponse(String prompt, Scanner scanner) {
    System.out.println(prompt);
    return scanner.nextInt();
  }

  /**
   * Prints the question answer out to the user.
   *
   * @param question a question/answer pair
   */
  public void displayAnswer(Question question) {
    System.out.println(question.getAnswer());
  }

  /**
   * Displays the final statistics of a study session.
   *
   * @param finalStats the final statistics of a study session
   */
  public void displayFinalScreen(ArrayList<String> finalStats) {
    for (String s : finalStats) {
      System.out.println(s);
    }
  }

}
