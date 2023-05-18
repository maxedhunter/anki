package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for the markdown file writer.
 */
class MdWriterTest {

  static final String SAMPLE_INPUTS_DIRECTORY = "src/test/resources/sampleFiles2";

  /**
   * Tests writing to a file.
   */
  @Test
  void testWriteToFile() {
    List<String> actualContent;

    MdWriter mdWriter1 = new MdWriter(SAMPLE_INPUTS_DIRECTORY + "/testingOutput.md");

    ArrayList<String> expectedContent = new ArrayList<>(Arrays.asList("one", "two", "three"));

    mdWriter1.writeToFile(expectedContent);

    try {
      actualContent = Files.readAllLines(
          Path.of(SAMPLE_INPUTS_DIRECTORY + "/testingOutput.md"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ArrayList<String> actualContentArrayList = new ArrayList<>(actualContent);

    assertEquals(expectedContent, actualContentArrayList);
  }

  /**
   * Tests exceptions when writing to a file.
   */
  @Test
  void testWriteToFileException() {
    MdWriter mdWriter = new MdWriter("badfile.txt");
    assertThrows(IllegalArgumentException.class,
        () -> mdWriter.writeToFile(new ArrayList<>()));


    MdWriter mdWriter1 = new MdWriter("path/this/doesnt/exist/beepboop.md");
    assertThrows(RuntimeException.class,
        () -> mdWriter1.writeToFile(new ArrayList<>()));
  }
}