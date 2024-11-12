package seedu.planpal.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import seedu.planpal.exceptions.PlanPalExceptions;
import seedu.planpal.utility.filemanager.BackUpManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BackUpManagerTest {
    @TempDir
    Path tempDir;

    @BeforeEach
    // Code for set up function was from ChatGPT
    public void setUp() {
        String sourceFolder = tempDir.resolve("source").toString();
        new File(sourceFolder).mkdirs();
        try {
            File testFile = new File(sourceFolder, "test.txt");
            Files.write(testFile.toPath(), "Test data".getBytes());
        } catch (IOException e) {
            fail("Setup failed: " + e.getMessage());
        }
    }

    @Test
    public void backupData_success() {
        try {
            String sourceFolder = tempDir.resolve("source").toString();
            String backupFolder = tempDir.resolve("backup").toString();
            BackUpManager.backupData(sourceFolder, backupFolder);
            File backupFile = new File(backupFolder, "data.zip");
            assertTrue(backupFile.exists(), "Backup should successfully create a zip file.");
        } catch (PlanPalExceptions e) {
            fail("Backup should not fail: " + e.getMessage());
        }
    }

    @Test
    public void restoreData_success() {
        try {
            String sourceFolder = tempDir.resolve("source").toString();
            String backupFolder = tempDir.resolve("backup").toString();
            String extractFolder = tempDir.resolve("extract").toString();
            BackUpManager.backupData(sourceFolder, backupFolder);
            BackUpManager.restoreData(backupFolder, extractFolder);
            File extractDir = new File(extractFolder);
            assertTrue(extractDir.exists() && extractDir.isDirectory(),
                    "Extract directory should exist after restore.");
            assertTrue(extractDir.list().length > 0,
                    "Restore should populate the extract directory.");
        } catch (PlanPalExceptions e) {
            fail("Restore should not fail: " + e.getMessage());
        }
    }

    @Test
    public void backupData_failure_invalidSource() {
        String invalidSourceFolder = tempDir.resolve("invalid_source").toString();
        String backupFolder = tempDir.resolve("backup").toString();
        assertThrows(PlanPalExceptions.class, () -> BackUpManager.backupData(invalidSourceFolder, backupFolder),
                "Source directory does not exist.");
    }

    @Test
    public void restoreData_failure_noBackupFile() {
        String backupFolder = tempDir.resolve("backup").toString();
        String extractFolder = tempDir.resolve("extract").toString();
        assertThrows(PlanPalExceptions.class, () -> BackUpManager.restoreData(backupFolder, extractFolder),
                "Backup zip file does not exist.");
    }
}
