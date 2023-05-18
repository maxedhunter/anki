package cs3500.pa01;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * Represents a markdown file visitor.
 */
public class MdVisitor implements FileVisitor<Path> {
  private ArrayList<Path> mds = new ArrayList<>();
  private boolean visited = false;

  /**
   * Returns the list of all the markdown files
   * visited ending in ".md".
   *
   * @return the list of markdown files
   */
  public ArrayList<Path> getMds() {
    if (visited) {
      return this.mds;
    } else {
      throw new IllegalStateException("No files have been visited yet!");
    }
  }

  /**
   * Changes visited to true and
   * returns continue.
   *
   * @param dir   a reference to the directory
   * @param attrs the directory's basic attributes
   * @return continue
   * @throws IOException if an I/O error occurs
   */
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
    visited = true;
    return CONTINUE;
  }

  /**
   * Records markdown files, changes
   * visited to true, and returns continue.
   *
   * @param file  a reference to the file
   * @param attrs the file's basic attributes
   * @return continue
   * @throws IOException if an I/O error occurs
   */
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    String f = file.getFileName().toString();

    if (f.endsWith(".md")) {
      mds.add(file);
    }
    visited = true;
    return CONTINUE;
  }

  /**
   * Changes visited to true,
   * and returns continue.
   *
   * @param file a reference to the file
   * @param exc  the I/O exception that prevented the file from being visited
   * @return continue
   * @throws IOException if an I/O error occurs
   */
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
    fileVisitHelp(exc);
    visited = true;
    return CONTINUE;
  }

  /**
   * Handles IOExceptions from
   * visitFileFailed.
   *
   * @param exc  the I/O exception that prevented the file from being visited
   */
  void fileVisitHelp(IOException exc) {
    try {
      throw exc;
    } catch (IOException e) {
      new Exception("Cannot visit the file");
    }
  }

  /**
   * Changes visited to true and
   * returns continue.
   *
   * @param dir a reference to the directory
   * @param exc {@code null} if the iteration of the directory completes without
   *            an error; otherwise the I/O exception that caused the iteration
   *            of the directory to complete prematurely
   * @return continue
   * @throws IOException if an I/O error occurs
   */
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
    visited = true;
    return CONTINUE;
  }
}
