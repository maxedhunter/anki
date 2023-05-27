package cs3500.pa02.studysession.model;

import cs3500.pa02.studysession.Question;
import cs3500.pa02.studysession.QuestionLabel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a study session model.
 */
public class StudySessionModel {
  ArrayList<Question> studyQuestions = new ArrayList<>();

  /**
   * Parses all the questions present in a file.
   *
   * @param pathName file with questions
   * @return the list of questions
   * @throws IOException if read all lines fails
   */
  public ArrayList<Question> parseQuestions(String pathName) throws IOException {
    ArrayList<Question> questions = new ArrayList<>();
    Path path = Path.of(pathName);
    List<String> lines;

    if (!pathName.endsWith(".sr")) {
      throw new IllegalArgumentException("The path provided must end in .sr");
    }

    if (Files.exists(path)) {
      lines = Files.readAllLines(path);
    } else {
      throw new IllegalArgumentException("File does not exist");
    }

    for (String s : lines) {
      Question temp = stringToQuestion(s);
      questions.add(temp);
    }

    studyQuestions = questions;
    return questions;
  }

  /**
   * Converts a string into a question.
   *
   * @param s a string representing a question
   * @return the question form of the string
   */
  public Question stringToQuestion(String s) {
    Question resultingQuestion;

    if (!s.contains("_") || !s.contains("|")) {
      throw new IllegalArgumentException("This string is not a properly formatted question");
    } else {
      String question;
      String answer;
      QuestionLabel difficulty;

      question = s.substring(0, s.indexOf("_"));
      answer = s.substring(s.indexOf("_") + 1, s.indexOf("|"));
      difficulty = QuestionLabel.valueOf(s.substring(s.indexOf("|") + 1));

      resultingQuestion = new Question(question, answer, difficulty);
    }

    return resultingQuestion;
  }

  /**
   * Updates the list of questions with the provided difficulty.
   *
   * @param question   user answered question
   * @param difficulty user assigned difficulty
   */
  public void updateQuestion(Question question, QuestionLabel difficulty) {
    for (Question q : studyQuestions) {
      if (q.sameQuestion(question)) {
        q.updateLabel(difficulty);
      }
    }
  }
}
