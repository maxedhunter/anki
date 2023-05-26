package cs3500.pa02;

import cs3500.pa02.studysession.Question;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles a file by reformatting it.
 */
public class FileHandler {
  private ArrayList<String> content = new ArrayList<>();
  private final ArrayList<Question> questions = new ArrayList<>();
  private final StringBuilder bracketedContent = new StringBuilder();
  private String questionString;
  boolean generatingQuestion = false;

  /**
   * Creates a summarized form of the file and
   * returns the appropriate headings and phrases.
   *
   * @param p the provided file path
   * @return the summarized contents of the file
   */
  public ArrayList<String> createContent(Path p) throws IllegalArgumentException {
    List<String> lines;

    // error handling
    try {
      lines = Files.readAllLines(p);
    } catch (IOException e) {
      throw new IllegalArgumentException("File does not exist");
    }

    for (String s : lines) {
      // first heading
      if (s.startsWith("# ")) {
        content.add(s);
      }
      if (s.startsWith("##")) {
        content.add("\n" + s);
      }
      if (s.contains("[[") || s.contains("]]") || s.contains(":::")) {
        bracketHandler(s);
      }
    }

    ArrayList<String> temp = content;
    content = new ArrayList<>();

    return temp;
  }

  /**
   * Processes the brackets and adds them
   * to the content in the correct order.
   *
   * @param s a string read from the file
   */
  private void bracketHandler(String s) {
    // if a question is involved
    if (s.contains(":::")) {
      questionHandler(s);
    }

    // handles important phrases
    if (s.contains("[[") && s.contains("]]") && !s.contains(":::")) {
      int begin = s.indexOf("[[") + 2;
      int end = s.indexOf("]]");

      content.add("- " + s.substring(begin, end));

      bracketHandler(s.substring(end + 2));
    }

    // beginning of an important phrase OR question
    if (s.contains("[[") && !s.contains("]]") && !s.contains(":::")) {
      int begin = s.indexOf("[[") + 2;
      bracketedContent.append(s.substring(begin));
    }

    // end of an important phrase OR question/answer
    if (s.contains("]]") && !s.contains("[[") && !s.contains(":::")) {
      bracketedContent.append(s, 0, s.indexOf("]]"));

      if (generatingQuestion) {
        String answer = bracketedContent.toString().trim();
        Question question = new Question(questionString, answer);
        questions.add(question);
        generatingQuestion = false;
      } else {
        bracketedContent.insert(0, "- ");
        content.add(bracketedContent.toString().trim());
      }

      bracketedContent.setLength(0);
    }
  }

  /**
   * Properly formats strings containing questions
   * or parts of questions.
   *
   * @param s string containing a question
   */
  private void questionHandler(String s) {
    // question contains on one line
    if (s.contains("[[") && s.contains(":::") && s.contains("]]")) {

      int questionBegin = s.indexOf("[[") + 2;
      int questionEnd = s.indexOf(":::");

      int answerBegin = s.indexOf(":::") + 3;
      int answerEnd = s.indexOf("]]");

      Question question = new Question(s.substring(questionBegin, questionEnd),
          s.substring(answerBegin, answerEnd));
      questions.add(question);

    }

    // question format [[ ::: \n ]]
    if (s.contains("[[") && s.contains(":::") && !s.contains("]]")) {
      int questionBegin = s.indexOf("[[") + 2;
      int questionEnd = s.indexOf(":::");

      questionString = s.substring(questionBegin, questionEnd);
      bracketedContent.append(s.substring(questionEnd + 3));
      generatingQuestion = true;
    }

    // question format [[ \n ::: ]]
    if (!s.contains("[[") && s.contains(":::") && s.contains("]]")) {
      int questionEnd = s.indexOf(":::");
      bracketedContent.append(s, 0, questionEnd);

      String questionText = bracketedContent.toString().trim();
      int answerBegin = s.indexOf(":::") + 3;
      int answerEnd = s.indexOf("]]");
      String answerText = s.substring(answerBegin, answerEnd);

      Question question = new Question(questionText, answerText);
      questions.add(question);

      generatingQuestion = false;
      bracketedContent.setLength(0);
    }

    // question format: [[ \n ::: \n ]]
    if (!s.contains("[[") && s.contains(":::") && !s.contains("]]")) {
      int questionEnd = s.indexOf(":::");
      bracketedContent.append(s, 0, questionEnd);
      questionString = bracketedContent.toString().trim();

      generatingQuestion = true;
      bracketedContent.setLength(0);
      bracketedContent.append(s.substring(questionEnd + 3));
    }
  }

  /**
   * Returns the questions formatted correctly
   * from an md file.
   *
   * @return questions from the md file
   */
  public ArrayList<Question> getQuestions() {
    return questions;
  }
}
