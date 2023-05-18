package cs3500.pa01;

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
  private StringBuilder bracketedContent = new StringBuilder();

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
      if (s.contains("[[") || s.contains("]]")) {
        bracketHandler(s);
      }
    }

    ArrayList<String> temp = content;
    content = new ArrayList<>();

    return temp;
  }

  private void bracketHandler(String s) {

    // if they are in the same line, handles multiple
    if (s.contains("[[") && s.contains("]]")) {
      int begin = s.indexOf("[[") + 2;
      int end = s.indexOf("]]");

      content.add("- " + s.substring(begin, end));

      bracketHandler(s.substring(end + 2));
    }

    // beginning of an important phrase
    if (s.contains("[[") && !s.contains("]]")) {
      int begin = s.indexOf("[[") + 2;
      bracketedContent.append("- ").append(s.substring(begin));
    }

    // end of an important phrase
    if (s.contains("]]") && !s.contains("[[")) {
      bracketedContent.append(s, 0, s.indexOf("]]"));
      content.add(bracketedContent.toString().trim());
      bracketedContent.setLength(0);
    }
  }
}
