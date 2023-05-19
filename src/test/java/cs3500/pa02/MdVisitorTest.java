package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for the markdown file visitor
 */
class MdVisitorTest {
  static final String SAMPLE_INPUTS_DIRECTORY = "src/test/resources/sampleFiles";

  @Test
  void getMds() {
    MdVisitor mdv = new MdVisitor();

    assertThrows(IllegalStateException.class,
        () -> mdv.getMds());

    try {
      Files.walkFileTree(Path.of(SAMPLE_INPUTS_DIRECTORY), mdv);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ArrayList<Path> expectedFiles = new ArrayList<>();
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/arrays.md"));
    expectedFiles.add(Path.of(SAMPLE_INPUTS_DIRECTORY + "/vectors.md"));

    ArrayList<Path> actualFiles = mdv.getMds();

    assertEquals(2, actualFiles.size());
    // assertArrayEquals(expectedFiles.toArray(), actualFiles.toArray());
  }

  @Test
  void testVisitFileFailed() {
    MdVisitor mdv = new MdVisitor();
    FileVisitResult result = null;
    try {
      result = mdv.visitFileFailed(null, new IOException());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals(FileVisitResult.CONTINUE, result);
  }
}