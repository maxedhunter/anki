package cs3500.pa02;

import cs3500.pa02.studysession.controller.StudySessionController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point, makes method calls.
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    MdVisitor mdVisitor = new MdVisitor();
    MdOrderFormatter mdOrderFormatter = new MdOrderFormatter();

    // if the argument length is correct
    if (args.length >= 3) {
      try {
        Files.walkFileTree(Path.of(args[0]), mdVisitor);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      ArrayList<Path> paths = mdVisitor.getMds();
      ArrayList<String> orderedContent =
          mdOrderFormatter.orderContent(paths, OrderingFlag.valueOf(args[1]));

      MdWriter mdWriter = new MdWriter(args[2]);
      mdWriter.writeToFile(orderedContent);

    } else if (args.length == 0) {
      StudySessionController controller = new StudySessionController();
      try {
        controller.run(new Scanner(System.in));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      throw new IllegalArgumentException("Please format the following seperated by whitespace: "
          + "a file path containing markdown notes you would like to summarize, "
          + "an ordering flag (FILENAME, CREATED, or MODIFIED), "
          + "an output path for your new markdown summary.");
    }
  }
}