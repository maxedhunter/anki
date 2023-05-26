package cs3500.pa02;

import cs3500.pa02.studysession.Question;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Represents a markdown file writer.
 */
public class MdWriter {
  private final Path writeTo;

  MdWriter(String s) {
    writeTo = Path.of(s);
  }

  /**
   * Writes ordered content to the markdown file.
   *
   * @param orderedContent the content to be written to the file
   */
  public void writeToFile(ArrayList<String> orderedContent) {
    if (writeTo.toString().endsWith(".md")) {
      try {
        Files.write(writeTo, orderedContent);
      } catch (IOException e) {
        throw new RuntimeException("Unable to write to file.");
      }
    } else {
      throw new IllegalArgumentException("Please provide an markdown file path.");
    }
  }

  /**
   * Writes the questions to a separate .sr file.
   *
   * @param questions generated from a given .md file
   */
  public void writeToFileQuestions(ArrayList<Question> questions) {
    ArrayList<String> questionStrings = new ArrayList<>();
    for (Question question : questions) {
      questionStrings.add(question.toString());
    }

    if (writeTo.toString().endsWith(".md")) {
      String questionFileName =
          writeTo.toString().substring(0, writeTo.toString().indexOf(".md")) + ".sr";
      Path p = Path.of(questionFileName);

      try {
        Files.write(p, questionStrings);
      } catch (IOException e) {
        throw new RuntimeException("Unable to write to file.");
      }
    } else {
      throw new IllegalArgumentException("Please provide an markdown file path ending in .md");
    }
  }
}
