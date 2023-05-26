package cs3500.pa02;

import cs3500.pa02.studysession.Question;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents properly ordering the output
 * based off of the passed OrderingFlag.
 */
public class MdOrderFormatter {
  private final ArrayList<String> orderedContent = new ArrayList<>();
  private final ArrayList<Question> questions = new ArrayList<>();

  private boolean ordered = false;

  /**
   * Returns the ordered content
   *
   * @return ordered content
   */
  public ArrayList<String> getOrderedContent() {
    if (ordered) {
      return this.orderedContent;
    } else {
      throw new IllegalStateException("Content has not yet been ordered.");
    }
  }

  /**
   * Sorts a given arraylist of paths by the specified order.
   *
   * @param paths        list of markdown file paths
   * @param orderingFlag the order to be sorted in
   * @return sorted paths by orderingFlag
   */
  public ArrayList<Path> orderPath(ArrayList<Path> paths, OrderingFlag orderingFlag) {
    if (orderingFlag == OrderingFlag.FILENAME) {
      Collections.sort(paths, Comparator.comparing(Path::getFileName));
    } else if (orderingFlag == OrderingFlag.CREATED) {
      Collections.sort(paths, Comparator.comparing(this::getFileCreationTime));
    } else if (orderingFlag == OrderingFlag.MODIFIED) {
      Collections.sort(paths, Comparator.comparing(this::getFileLastModifiedTime));
    }
    ordered = true;
    return paths;
  }

  /**
   * Returns the file creation time
   * and throws an error if unable to.
   *
   * @param p path of a file
   * @return the creation time
   */
  private FileTime getFileCreationTime(Path p) {
    try {
      BasicFileAttributes att = Files.readAttributes(p, BasicFileAttributes.class);
      return att.creationTime();
    } catch (IOException e) {
      throw new RuntimeException("Could not get file creation time.");
    }
  }

  /**
   * Returns the file last modified time
   * and throws an error if unable to.
   *
   * @param p path of a file
   * @return the last modified time
   */
  private FileTime getFileLastModifiedTime(Path p) {
    try {
      BasicFileAttributes att = Files.readAttributes(p, BasicFileAttributes.class);
      return att.lastModifiedTime();
    } catch (IOException e) {
      throw new RuntimeException("Could not get file last modified time.");
    }
  }

  /**
   * Returns the content in the order
   * specified by a list of file paths.
   *
   * @param paths a list of markdown files
   * @return content in the specified order
   */
  public ArrayList<String> orderContent(ArrayList<Path> paths, OrderingFlag orderingFlag) {
    FileHandler fileHandler = new FileHandler();
    paths = this.orderPath(paths, orderingFlag);

    for (int i = 0; i < paths.size(); i++) {
      Path p = paths.get(i);
      ArrayList<String> temp = fileHandler.createContent(p);
      orderedContent.addAll(temp);
      if (i < paths.size() - 1) {
        orderedContent.add("");
      }
    }
    return orderedContent;
  }

  /**
   * @param paths paths to search for questions
   * @return a list of questions
   */
  public ArrayList<Question> getQuestions(ArrayList<Path> paths) {
    for (Path path : paths) {
      FileHandler fileHandler = new FileHandler();

      Path p = path;
      fileHandler.createContent(p);
      ArrayList<Question> temp = fileHandler.getQuestions();
      questions.addAll(temp);
    }

    return questions;
  }
}
