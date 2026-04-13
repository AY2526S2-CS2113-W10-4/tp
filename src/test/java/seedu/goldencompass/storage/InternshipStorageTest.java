package seedu.goldencompass.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.goldencompass.internship.Internship;
import seedu.goldencompass.internship.InternshipList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class InternshipStorageTest {

    private static final String TEST_FILE_PATH = "test_internships.txt";
    private InternshipStorage internshipStorage;
    private InternshipList internshipList;

    @BeforeEach
    public void setUp() {
        internshipStorage = new InternshipStorage(TEST_FILE_PATH);
        internshipList = new InternshipList();
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void saveAndLoad_withStatuses_success() {
        Internship offerIntern = new Internship("Software Engineer", "Grab");
        offerIntern.markAsOffer();

        Internship rejectedIntern = new Internship("Data Analyst", "Shopee");
        rejectedIntern.markAsRejected();

        Internship pendingIntern = new Internship("Frontend Dev", "TikTok");

        internshipList.add(offerIntern);
        internshipList.add(rejectedIntern);
        internshipList.add(pendingIntern);

        internshipStorage.save(internshipList);
        ArrayList<Internship> loadedList = internshipStorage.load();

        assertEquals(3, loadedList.size());
        assertTrue(loadedList.get(0).hasOffer());
        assertTrue(loadedList.get(1).isRejected());
        assertFalse(loadedList.get(2).hasOffer());
        assertFalse(loadedList.get(2).isRejected());
    }

    @Test
    public void load_emptyFile_returnsEmptyList() {
        internshipStorage.save(internshipList);
        ArrayList<Internship> loadedList = internshipStorage.load();
        assertTrue(loadedList.isEmpty());
    }

    @Test
    public void load_corruptedAndShortLines_skipsInvalidEntries() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        testFile.createNewFile();
        FileWriter fw = new FileWriter(testFile);
        fw.write("Valid Title | Valid Company | OFFER\n");
        fw.write("Old Role | Old Company\n"); // ✨ Skipped: Only 2 parts (Legacy no longer supported)
        fw.write("A | B | PENDING\n"); // Skipped: Strings less than 2 characters
        fw.write("Another Title | Another Company | REJECTED\n");
        fw.close();

        ArrayList<Internship> loadedList = internshipStorage.load();

        assertEquals(2, loadedList.size(), "Should have skipped the corrupted, legacy, and short lines");
        assertTrue(loadedList.get(0).hasOffer());
        assertTrue(loadedList.get(1).isRejected());
    }

    @Test
    public void load_duplicateEntries_keepsFirstAndSkipsRest() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        testFile.createNewFile();
        FileWriter fw = new FileWriter(testFile);
        fw.write("Software Engineer | Google | PENDING\n");
        fw.write("Software Engineer | Google | OFFER\n"); // Duplicate, should be skipped
        fw.close();

        ArrayList<Internship> loadedList = internshipStorage.load();

        assertEquals(1, loadedList.size(), "Should only load the first unique internship");
        assertEquals("Software Engineer", loadedList.get(0).getTitle());
        assertFalse(loadedList.get(0).hasOffer(), "Should be PENDING, as the duplicate OFFER was skipped");
    }

    @Test
    public void load_irregularSpacing_parsesCorrectly() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        testFile.createNewFile();
        FileWriter fw = new FileWriter(testFile);
        fw.write("Backend Dev|Netflix|OFFER\n");
        fw.close();

        ArrayList<Internship> loadedList = internshipStorage.load();

        assertEquals(1, loadedList.size());
        assertEquals("Backend Dev", loadedList.get(0).getTitle());
        assertEquals("Netflix", loadedList.get(0).getCompanyName());
        assertTrue(loadedList.get(0).hasOffer());
    }
}
