package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Represents a markdown file writer.
 */
public class MdWriter {
  private Path writeTo;

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
}
