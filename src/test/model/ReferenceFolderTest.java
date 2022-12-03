package model;

import model.exceptions.CurrentFolderException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReferenceFolderTest {
    private ReferenceImage ref1;
    private ReferenceImage ref2;
    private ReferenceImage ref3;
    private ReferenceFolder folder1;
    private ReferenceFolder folder2;
    private ReferenceFolder folder3;
    private EventLog eventLog;
    private List<String> eventsLogged;

    @BeforeEach
    public void setUp() {
        eventLog = EventLog.getInstance();
        eventLog.clear();
        eventsLogged = new ArrayList<>();
        ref1 = new ReferenceImage("image1", "src/main/resources/artref-add.png");
        ref2 = new ReferenceImage("image2", "src/main/resources/artref-addcp.png");
        ref3 = new ReferenceImage("image3", "src/main/resources/artref-addfolder.png");
        folder1 = new ReferenceFolder("R1");
        folder2 = new ReferenceFolder("R2");
        folder3 = new ReferenceFolder("R3");
    }

    // tests for addRef
    @Test
    public void testAddOneRef() {
        boolean addRefImageSuccess = folder1.addRef(ref1);

        List<ReferenceImage> refs = folder1.getReferenceImages();
        assertEquals(1, refs.size());
        assertTrue(addRefImageSuccess);
        assertEquals(ref1, refs.get(0));

        for (Event event: eventLog) {
            eventsLogged.add(event.getDescription());
        }

        assertEquals("Event log cleared.", eventsLogged.get(0));
        assertEquals("Added image image1 to R1", eventsLogged.get(1));
    }

    @Test
    public void testAddRefSameRefMultipleTimes() {
        boolean addRef2Success = folder1.addRef(ref2);
        boolean addColour2AgainSuccess = folder1.addRef(ref2);

        assertTrue(addRef2Success);
        assertFalse(addColour2AgainSuccess);

        List<ReferenceImage> refs = folder1.getReferenceImages();
        assertEquals(1, refs.size());
        assertEquals(ref2, refs.get(0));

        for (Event event: eventLog) {
            eventsLogged.add(event.getDescription());
        }
        assertEquals("Added image image2 to R1", eventsLogged.get(1));
        assertEquals(2, eventsLogged.size());
    }

    @Test
    public void testAddThreeDifferentRefs() {
        boolean addRef1Success = folder1.addRef(ref1);
        boolean addRef2Success = folder1.addRef(ref2);
        boolean addRef3Success = folder1.addRef(ref3);

        assertTrue(addRef1Success && addRef2Success && addRef3Success);

        List<ReferenceImage> refs = folder1.getReferenceImages();
        assertEquals(3, refs.size());
        assertEquals(ref1, refs.get(0));
        assertEquals(ref2, refs.get(1));
        assertEquals(ref3, refs.get(2));

        for (Event event: eventLog) {
            eventsLogged.add(event.getDescription());
        }
        assertEquals("Added image image1 to R1", eventsLogged.get(1));
        assertEquals("Added image image2 to R1", eventsLogged.get(2));
        assertEquals("Added image image3 to R1", eventsLogged.get(3));

    }

    // tests for addSubFolder
    @Test
    public void testAddOneSubFolder() {
        boolean addSubFolderSuccess = folder1.addSubRefFolder(folder2);
        assertTrue(addSubFolderSuccess);
        assertEquals(1, folder1.getSubRefFolders().size());
        assertEquals(folder2, folder1.getSubRefFolders().get(0));

        for (Event event: eventLog) {
            eventsLogged.add(event.getDescription());
        }
        assertEquals("Added sub folder R2 to R1", eventsLogged.get(1));
    }

    @Test
    public void testAddMultipleDifferentSubFolders() {
        boolean addSubFolder2Success = folder1.addSubRefFolder(folder2);
        boolean addSubFolder3Success = folder1.addSubRefFolder(folder3);

        assertTrue(addSubFolder2Success);
        assertTrue(addSubFolder3Success);

        List<ReferenceFolder> subFolders = folder1.getSubRefFolders();
        assertEquals(2,subFolders.size());
        assertEquals(folder2, subFolders.get(0));
        assertEquals(folder3, subFolders.get(1));
        for (Event event: eventLog) {
            eventsLogged.add(event.getDescription());
        }
        assertEquals("Added sub folder R2 to R1", eventsLogged.get(1));
        assertEquals("Added sub folder R3 to R1", eventsLogged.get(2));
    }

    @Test
    public void testAddSameSubFolderMultipleTimes() {
        boolean addSubFolder2Success = folder1.addSubRefFolder(folder2);
        boolean addSubFolder2AgainSuccess = folder1.addSubRefFolder(folder2);

        assertTrue(addSubFolder2Success);
        assertFalse(addSubFolder2AgainSuccess);

        assertEquals(1, folder1.getSubRefFolders().size());
        assertEquals(folder2, folder1.getSubRefFolders().get(0));
        assertFalse(folder1.getSubRefFolders().size() == 2);

        for (Event event: eventLog) {
            eventsLogged.add(event.getDescription());
        }
        assertEquals("Added sub folder R2 to R1", eventsLogged.get(1));
        assertEquals(2, eventsLogged.size());
    }

    @Test
    public void testAddCurrentFolderAsSubFolder() {
        try {
            folder1.addSubRefFolder(folder1);
            fail("CurrentFolderException expected!");
        } catch (CurrentFolderException e) {
            // pass!
        }
    }

    // test for deleteSubRefFolder
    @Test
    public void testDelSubRefFolderExistingSubRefFolder() {
        folder1.addSubRefFolder(folder2);
        folder1.addSubRefFolder(folder3);

        boolean delSubFolder2Success = folder1.deleteSubRefFolder(folder2);
        assertTrue(delSubFolder2Success);

        assertEquals(1, folder1.getSubRefFolders().size());
        assertFalse(folder1.getSubRefFolders().contains(folder2));
        assertEquals(folder3, folder1.getSubRefFolders().get(0));

        for (Event event: eventLog) {
            eventsLogged.add(event.getDescription());
        }
        assertEquals("Deleted sub folder R2 from R1", eventsLogged.get(3));
    }

    @Test
    public void testDelSubFolderNonexistentSubFolder() {
        folder1.addSubRefFolder(folder2);

        boolean delSubFolder3Success = folder1.deleteSubRefFolder(folder3);
        assertFalse(delSubFolder3Success);
        assertEquals(1, folder1.getSubRefFolders().size());
        assertTrue(folder1.getSubRefFolders().contains(folder2));

        for (Event event: eventLog) {
            eventsLogged.add(event.getDescription());
        }
        assertEquals("Added sub folder R2 to R1", eventsLogged.get(1));
        assertTrue(eventsLogged.size() == 2);

    }

    @Test
    public void testDelSubFolderCurrentFolder() {
        try {
            folder1.deleteSubRefFolder(folder1);
            fail("Shouldn't have gotten to this line!");
        } catch (CurrentFolderException e) {
            // pass!
        }
    }

    // tests for deleteRef
    @Test
    public void testDeleteRefExistingRef() {
        folder1.addRef(ref1);
        folder1.addRef(ref2);

        boolean delRef1Success = folder1.deleteRef(ref1);
        assertTrue(delRef1Success);
        assertEquals(1, folder1.getReferenceImages().size());
        assertEquals(ref2, folder1.getReferenceImages().get(0));
        assertFalse(folder1.getReferenceImages().contains(ref1));

        for (Event event: eventLog) {
            eventsLogged.add(event.getDescription());
        }
        assertEquals("Deleted image image1 from R1", eventsLogged.get(3));
    }

    @Test
    public void testDeleteRefNonexistentRef() {
        boolean delRef2Success = folder1.deleteRef(ref2);
        assertFalse(delRef2Success);
        assertTrue(folder1.getReferenceImages().isEmpty());

        folder1.addRef(ref3);

        boolean delRef1Success = folder1.deleteRef(ref1);
        assertFalse(delRef1Success);

        assertEquals(1, folder1.getReferenceImages().size());
        assertEquals(ref3, folder1.getReferenceImages().get(0));

        for (Event event: eventLog) {
            eventsLogged.add(event.getDescription());
        }
        assertEquals(2, eventsLogged.size());
        assertEquals("Added image image3 to R1", eventsLogged.get(1));
    }

    // json tests
    @Test
    public void testToJsonNoSubFolderNoRefImage() {
        JSONObject folder1JsonObj = folder1.toJson();
        assertEquals("R1", folder1JsonObj.get("folderName"));
        assertEquals(0, folder1JsonObj.getJSONArray("listOfSubFolders").length());
        assertEquals(0, folder1JsonObj.getJSONArray("listOfImages").length());
    }

    @Test
    public void testToJsonWithSubFolders() {
        folder1.addSubRefFolder(folder2);

        JSONObject folder1JsonObj = folder1.toJson();
        assertEquals("R1", folder1JsonObj.get("folderName"));
        assertEquals(1, folder1JsonObj.getJSONArray("listOfSubFolders").length());
        assertEquals(0, folder1JsonObj.getJSONArray("listOfImages").length());

        JSONObject folder2JsonObj = (JSONObject) folder1JsonObj.getJSONArray("listOfSubFolders").get(0);
        assertEquals("R2", folder2JsonObj.get("folderName"));
        assertEquals(0, folder2JsonObj.getJSONArray("listOfSubFolders").length());
        assertEquals(0, folder2JsonObj.getJSONArray("listOfImages").length());
    }

    @Test
    public void testToJsonWithRefs() {
        folder1.addRef(ref1);
        folder1.addRef(ref2);
        JSONObject folder1JsonObj = folder1.toJson();
        JSONArray arrayOfRefImage = folder1JsonObj.getJSONArray("listOfImages");
        JSONObject ref1JsonObj = (JSONObject) arrayOfRefImage.get(0);
        JSONObject ref2JsonObj = (JSONObject) arrayOfRefImage.get(1);

        assertEquals(2, arrayOfRefImage.length());
        assertTrue(folder1JsonObj.getJSONArray("listOfSubFolders").isEmpty());
        assertEquals("image1", ref1JsonObj.get("imageName"));
        assertEquals("image2", ref2JsonObj.getString("imageName"));
        assertEquals("src/main/resources/artref-add.png", ref1JsonObj.getString("imageURL"));
        assertEquals("src/main/resources/artref-addcp.png", ref2JsonObj.getString("imageURL"));
    }

    // other tests
    @Test
    public void testIfSubRefFolderAlreadyExists() {
        folder1.addSubRefFolder(folder2);

        boolean folder2Exists = folder1.ifSubRefFolderAlreadyExists(folder2);
        boolean folder3Exists = folder1.ifSubRefFolderAlreadyExists(folder3);

        assertTrue(folder2Exists);
        assertFalse(folder3Exists);
    }

    @Test
    public void testIfRefExistsAlready() {
        folder1.addRef(ref1);

        boolean ref1exists = folder1.ifRefExistsAlready(ref1);
        boolean ref2exists = folder1.ifRefExistsAlready(ref2);

        assertTrue(ref1exists);
        assertFalse(ref2exists);

        ReferenceImage ref4 = new ReferenceImage("Ref4", "src/main/resources/artref-add.png");
        boolean ref4exists = folder1.ifRefExistsAlready(ref4);
        assertTrue(ref4exists);

        ReferenceImage ref5 = new ReferenceImage("R1", "src/main/resources/artref-add.png");
        boolean ref5exists = folder1.ifRefExistsAlready(ref5);
        assertTrue(ref5exists);

    }

    @Test
    public void testSetFolderName() {
        folder1.setFolderName("Folder 1");
        assertEquals("Folder 1", folder1.getFolderName());
    }

    @Test
    public void testClearImages() {
        folder1.addRef(ref1);
        folder1.addRef(ref2);
        folder1.clearImages();
        assertTrue(folder1.getReferenceImages().isEmpty());
        assertFalse(folder1.getReferenceImages().contains(ref1));
        assertFalse(folder1.getReferenceImages().contains(ref2));

        for (Event event: eventLog) {
            eventsLogged.add(event.getDescription());
        }
        assertEquals("Deleted all images from R1", eventsLogged.get(3));
    }


}
