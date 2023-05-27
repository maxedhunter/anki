package cs3500.pa02.studysession.model;

import cs3500.pa02.studysession.Question;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Represents writing to update the .sr file,
 * once a study session has been completed.
 */
public class StudySessionWriter {
  /**
   * Writes the questions to the provided file.
   *
   * @param questions questions to be written to file
   * @param path      the file where questions will be written
   */
  public void writeToFile(ArrayList<Question> questions, Path path) throws IOException {
    ArrayList<String> questionStrings = new ArrayList<>();
    for (Question q : questions) {
      questionStrings.add(q.toString());
    }

    if (!Files.exists(path)) {
      throw new IllegalArgumentException("File does not exist.");
    }

    if (path.toString().endsWith(".sr")) {
      new FileOutputStream(path.toFile()).close();

      Files.write(path, questionStrings);

    } else {
      throw new IllegalArgumentException("File must be a .sr file");
    }
  }
}
