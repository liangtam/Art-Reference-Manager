package persistence;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.*;

import java.io.IOException;
import java.util.List;

public class JsonReaderRefTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReaderRef reader = new JsonReaderRef("./data/iDunExist.json");
        try {
            reader.read();
            fail("IOException expected.");
        } catch (IOException e) {
            // pass!
        }
    }

    @Test
    public void testReaderListOfOneReferenceFolder() {
        JsonReaderRef reader = new JsonReaderRef("./data/testReaderListOfOneReferenceFolder.json");
        try {
            List<ReferenceFolder> referenceFolders = reader.read();
            assertEquals(1, referenceFolders.size());
            ReferenceFolder rf = referenceFolders.get(0);
            checkReferenceFolder("Ref 1", 0, 0 , rf);
        } catch (IOException e) {
            fail("Shouldn't have been not able to read file!");
        }
    }

    @Test
    public void testReaderListOfMultipleReferenceFolders() {
        JsonReaderRef reader = new JsonReaderRef("./data/testReaderListOfReferenceFolders.json");
        try {
            List<ReferenceFolder> referenceFolders = reader.read();
            assertEquals(2, referenceFolders.size());
            ReferenceFolder folder1 = referenceFolders.get(0);
            ReferenceImage image1 = folder1.getReferenceImages().get(0);
            ReferenceImage image2 = folder1.getReferenceImages().get(1);
            ReferenceFolder folder2akaSubFolderOfFolder1 = folder1.getSubRefFolders().get(0);

            checkReferenceFolder("Folder 1", 2, 1, folder1);
            checkReferenceFolder("Folder 2", 1, 0, folder2akaSubFolderOfFolder1);
            checkReferenceImage("Image 1", "src/main/resources/artref-add.png", image1);
            checkReferenceImage("Image 2", "src/main/resources/artref-addcp.png", image2);

            ReferenceFolder folder3 = referenceFolders.get(1);
            ReferenceImage image4 = folder3.getReferenceImages().get(0);

            checkReferenceImage("Image 4", "src/main/resources/artref-bg.png", image4);
            checkReferenceFolder("Folder 3", 1, 0, folder3);
        } catch (IOException e) {
            fail("Shouldn't have been not able to read file!");
        }
    }
}
