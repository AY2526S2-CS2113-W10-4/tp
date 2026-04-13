package seedu.goldencompass.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import seedu.goldencompass.command.Executor;
import seedu.goldencompass.internship.InternshipList;
import seedu.goldencompass.internship.InterviewList;
import seedu.goldencompass.parser.Parser;
import seedu.goldencompass.operation.OperationHistory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AliasStorageTest {

    @TempDir
    Path tempDir;

    /**
     * Helper method to create a barebones Executor for testing.
     * We pass nulls for Parser and OperationHistory because we only need
     * the alias functionality, not full command execution.
     */
    private Executor createTestExecutor() {
        // Provide actual empty objects instead of nulls to satisfy the command assertions!
        return new Executor(new Parser(), new InternshipList(), new InterviewList(), new OperationHistory());
    }

    @Test
    public void save_customAliases_savesCorrectly() throws IOException {
        String testPath = tempDir.resolve("testSaveAliases.txt").toString();
        AliasStorage storage = new AliasStorage(testPath);

        Map<String, String> mapToSave = new HashMap<>();
        mapToSave.put("ls", "list");        // Custom alias -> Should be saved
        mapToSave.put("add", "add");        // Default command -> Should NOT be saved

        storage.save(mapToSave);

        // Verify the file was created and contains only the custom alias
        File savedFile = new File(testPath);
        assertTrue(savedFile.exists());

        Scanner scanner = new Scanner(savedFile);
        String savedLine = scanner.nextLine();
        assertEquals("ls | list", savedLine);
        assertFalse(scanner.hasNext(), "File should only contain one line");
        scanner.close();
    }

    @Test
    public void load_validAndCorruptedAliases_loadsOnlyValid() throws IOException {
        String testPath = tempDir.resolve("testLoadAliases.txt").toString();
        File testFile = new File(testPath);
        FileWriter fw = new FileWriter(testFile);

        // 1. Valid alias
        fw.write("ls | list\n");
        // 2. Invalid: "random" is not a real command (Triggers RoaringCat's validation!)
        fw.write("invalid_alias | random\n");
        // 3. Corrupted line
        fw.write("corrupted line without pipes\n");
        fw.close();

        AliasStorage storage = new AliasStorage(testPath);
        Executor executor = createTestExecutor();

        // The executor starts with a baseline of default commands mapped to themselves
        int initialSize = executor.getAliasMap().size();

        storage.load(executor);
        Map<String, String> loadedMap = executor.getAliasMap();

        // Assertions
        assertEquals(initialSize + 1, loadedMap.size(), "Should have added exactly 1 valid alias");
        assertEquals("list", loadedMap.get("ls"), "Valid alias should be mapped");
        assertFalse(loadedMap.containsKey("invalid_alias"), "Invalid alias should have been rejected by Executor");
    }

    @Test
    public void load_emptyOrNonExistentFile_doesNothing() {
        String testPath = tempDir.resolve("empty.txt").toString();
        AliasStorage storage = new AliasStorage(testPath);
        Executor executor = createTestExecutor();

        int initialSize = executor.getAliasMap().size();

        storage.load(executor);

        // Should just return silently without crashing or changing the map
        assertEquals(initialSize, executor.getAliasMap().size(), "Executor map should remain unchanged");
    }
}
