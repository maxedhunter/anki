package cs3500.pa02.studysession.controller;

import cs3500.pa02.studysession.Question;
import cs3500.pa02.studysession.QuestionLabel;
import cs3500.pa02.studysession.model.StudySessionModel;
import cs3500.pa02.studysession.model.StudySessionWriter;
import cs3500.pa02.studysession.view.StudySessionView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Represents a study session controller.
 */
public class StudySessionController {
  private final ArrayList<Question> questions = new ArrayList<>();
  private Path path;
  private int numberOfQuestions;
  private final StudySessionView view = new StudySessionView();
  private final StudySessionModel model = new StudySessionModel();
  private final StudySessionWriter writer = new StudySessionWriter();

  private int hardToEasy = 0;
  private int easyToHard = 0;

  private boolean studyingOver = false;

  /**
   * Represents running a study session program.
   */
  public void run(Scanner scanner) throws IOException {
    setUpPath(scanner);
    setUpNumberOfQuestions(scanner);

    setUpQuestions();

    // checks the size of number of questions
    if(numberOfQuestions > questions.size()) {
      numberOfQuestions = questions.size();
    }

    questionHandler(scanner);
    handleEnding();
    writer.writeToFile(questions, path);
  }


  /**
   * Sets up the path field for a controller.
   *
   * @param scanner for user input
   * @return path string
   */
  public String setUpPath(Scanner scanner) {
    String pathName = view.getUserSrFilePath(
        "Welcome to your study session!" +
            "\nWhich .sr question bank file would you like to load?",
        scanner);
    path = Path.of(pathName);
    if(!path.toString().endsWith(".sr") || !Files.exists(path)) {
      throw new IllegalArgumentException("Please provide a valid .sr question bank file.");
    }
    return pathName;
  }

  /**
   * Sets up the number of questions the user requested.
   *
   * @param scanner for user input
   */
  public int setUpNumberOfQuestions(Scanner scanner) {
    numberOfQuestions =
        view.getNumberOfQuestions("How many questions would you like to practice?",
            scanner);

    return numberOfQuestions;
  }


  /**
   * Sets up the questions to ask the user.
   *
   * @return the arraylist of questions
   * @throws IOException if parseQuestions fails
   */
  public ArrayList<Question> setUpQuestions() throws IOException {
    model.parseQuestions(path.toString());
    model.splitQuestions();

    ArrayList<Question> hardQuestions = model.getHardQuestions();
    ArrayList<Question> easyQuestions = model.getEasyQuestions();

    Collections.shuffle(hardQuestions);
    Collections.shuffle(easyQuestions);

    hardQuestions.addAll(easyQuestions);

    questions.addAll(hardQuestions);

    return questions;
  }


  /**
   * Handles the question display, input, and output.
   * @param scanner for user input
   * @return the updated questions
   * @throws IOException should parseQuestions fa
   */
  public ArrayList<Question> questionHandler(Scanner scanner) throws IOException {
    for (int i = 0; i < numberOfQuestions; i++) {
      Question question = questions.get(i);
      view.displayQuestion(question);

      int response = view.getUserIntegerResponse(
          "\nPick one of the following:\n(1) Easy\n(2) Hard\n(3) Show answer\n(4) Exit",
          scanner);

      // make sure the arguments are valid
      if (!(response > 0 && response < 5)) {
        throw new IllegalArgumentException("Please choose a valid option: 1, 2 or 3.");
      }

      // update label
      if (response == 1 && question.getLabel().equals(QuestionLabel.HARD)) {
        hardToEasy = hardToEasy + 1;
        model.updateQuestion(question, QuestionLabel.EASY);
      }
      if (response == 2 && question.getLabel().equals(QuestionLabel.EASY)) {
        easyToHard = easyToHard + 1;
        model.updateQuestion(question, QuestionLabel.HARD);
      }

      if (response == 4) {
        break;
      }

      view.displayAnswer(question);
    }

    studyingOver = true;
    return model.parseQuestions(path.toString());
  }

  /**
   * Handles the ending of the studying session.
   */
  public void handleEnding() {
    if (studyingOver) {
      int newNumberOfEasyQuestions = 0;
      int newNumberOfHardQuestions = 0;
      for (Question q : questions) {
        if(q.getLabel().equals(QuestionLabel.EASY)) {
          newNumberOfEasyQuestions = newNumberOfEasyQuestions + 1;
        } else if(q.getLabel().equals(QuestionLabel.HARD)) {
          newNumberOfHardQuestions = newNumberOfHardQuestions + 1;
        }
      }

      ArrayList<String> finalStats = new ArrayList<>(
          Arrays.asList("\nCongrats! ----", "You answered " + numberOfQuestions + " questions!",
              hardToEasy + " questions went from hard to easy.",
              easyToHard + " questions went from easy to hard",
              "\nCurrent counts in the question bank ---",
              newNumberOfEasyQuestions + " easy questions.",
              newNumberOfHardQuestions + " hard questions."));

      view.displayFinalScreen(finalStats);
    } else {
      throw new IllegalStateException("User not yet done studying, cannot create final stats");
    }
  }
}
