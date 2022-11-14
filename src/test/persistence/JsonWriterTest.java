package persistence;

import model.Colour;
import model.ColourPalette;
import model.ReferenceFolder;
import model.ReferenceImage;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// **heavily based on JsonSerializationDemo project**
// Tests for the methods in JsonWriter class
public class JsonWriterTest extends JsonTest{
    //the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    public void testWriterInvalidFile() {
        try {
            ColourPalette cp = new ColourPalette("Sunset");
            List<ColourPalette> cps = new ArrayList<>();
            cps.add(cp);

            JsonWriter writer = new JsonWriter("./data/\0illegalFileName.json");
            writer.open();
            fail("IOException was expected!");
        } catch (IOException e) {

        }
    }

    @Test
    public void testWriterEmptyListOfColourPalettes() {
        try {
            List<ColourPalette> cps = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyListOfColourPalettes.json");
            writer.open();
            writer.writeListOfColourPalettes(cps);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyListOfColourPalettes.json");
            cps = reader.read();
            assertEquals(0, cps.size());
        } catch (IOException e) {
            fail("IOException shouldn't have been thrown!");
        }
    }

    @Test
    public void testWriterListOfOneColourPalette() {
        try {
            List<ColourPalette> cps = new ArrayList<>();
            ColourPalette cp = new ColourPalette("Colour Palette 1");
            cps.add(cp);

            JsonWriter writer = new JsonWriter("./data/testWriterListOfOneColourPalette.json");
            writer.open();
            writer.writeListOfColourPalettes(cps);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterListOfOneColourPalette.json");
            cps = reader.read();
            assertEquals(1, cps.size());
            ColourPalette cpFromFile = cps.get(0);
            checkColourPalette("Colour Palette 1", 0, 0 , cpFromFile);

        } catch (IOException e) {
            fail("IOException shouldn't have been thrown!");
        }
    }

    @Test
    public void testWriterListOfMultipleColourPalettes() {
        try {
            List<ColourPalette> cps = new ArrayList<>();
            ColourPalette cp1 = new ColourPalette("Sunset");
            ColourPalette cp2 = new ColourPalette("Ocean");
            ColourPalette subCP1 = new ColourPalette("Sky");
            Colour white = new Colour("White", "#000000");
            Colour red = new Colour("Red", "#FF0000");
            Colour blue = new Colour("Blue", "#0000FF");
            Colour redOrange = new Colour("Red Orange", "#FF2500");

            subCP1.addColour(white);
            cp1.addSubColourPalette(subCP1);
            cp1.addColour(red);
            cp1.addColour(redOrange);
            cp2.addColour(blue);

            cps.add(cp1);
            cps.add(cp2);

            JsonWriter writer = new JsonWriter("./data/testWriterListOfMultipleColourPalettes.json");
            writer.open();
            writer.writeListOfColourPalettes(cps);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterListOfMultipleColourPalettes.json");
            cps = reader.read();

            assertEquals(2, cps.size());
            ColourPalette sunsetCP = cps.get(0);
            Colour sunsetColorRed = sunsetCP.getColours().get(0);
            Color redColor = new Color(255, 0, 0);
            Colour sunsetColorOrange = sunsetCP.getColours().get(1);
            Color redOrangeColor = new Color(255, 37, 0);
            ColourPalette sunsetSubCP = sunsetCP.getSubColourPalettes().get(0);

            checkColourPalette("Sunset", 2, 1, sunsetCP);
            checkColourPalette("Sky", 1, 0, sunsetSubCP);
            checkColour("Red Orange", "#FF2500", redOrangeColor, sunsetColorOrange);
            checkColour("Red", "#FF0000", redColor, sunsetColorRed);

            ColourPalette oceanCP = cps.get(1);
            Colour oceanColorBlue = oceanCP.getColours().get(0);
            Color blueColor = new Color(0,0,255);

            checkColour("Blue", "#0000FF", blueColor, oceanColorBlue);
            checkColourPalette("Ocean", 1, 0, oceanCP);
        } catch (IOException e) {
            fail("IOException shouldn't have been thrown!");
        }
    }

    @Test
    public void testWriterListOfOneReferenceFolder() {
        try {
            List<ReferenceFolder> rfs = new ArrayList<>();
            ReferenceFolder rf = new ReferenceFolder("Folder 1");
            rfs.add(rf);

            JsonWriter writer = new JsonWriter("./data/testWriterListOfOneReferenceFolder.json");
            writer.open();
            writer.writeListOfReferenceFolders(rfs);
            writer.close();

            JsonReaderRef reader = new JsonReaderRef("./data/testWriterListOfOneReferenceFolder.json");
            rfs = reader.read();
            assertEquals(1, rfs.size());
            ReferenceFolder rfFromFile = rfs.get(0);
            checkReferenceFolder("Folder 1", 0, 0 , rfFromFile);

        } catch (IOException e) {
            fail("IOException shouldn't have been thrown!");
        }
    }

    @Test
    public void testWriteListOfMultipleReferenceFolders() {
        try {
            List<ReferenceFolder> rfs = new ArrayList<>();
            ReferenceFolder folder1 = new ReferenceFolder("Folder 1");
            ReferenceFolder folder2 = new ReferenceFolder("Folder 2");
            ReferenceFolder folder3 = new ReferenceFolder("Folder 3");
            ReferenceImage image1 = new ReferenceImage("Image 1", "src/main/resources/artref-add.png");
            ReferenceImage image2 = new ReferenceImage("Image 2", "src/main/resources/artref-addcp.png");
            ReferenceImage image3 = new ReferenceImage("Image 3", "src/main/resources/artref-addfolder.png");
            ReferenceImage image4 = new ReferenceImage("Image 4", "src/main/resources/artref-bg.png");

            folder1.addSubRefFolder(folder2);
            folder2.addRef(image3);
            folder1.addRef(image1);
            folder1.addRef(image2);
            folder3.addRef(image4);

            rfs.add(folder1);
            rfs.add(folder3);

            JsonWriter writer = new JsonWriter("./data/testWriterListOfMultipleReferenceFolders.json");
            writer.open();
            writer.writeListOfReferenceFolders(rfs);
            writer.close();

            JsonReaderRef reader = new JsonReaderRef("./data/testWriterListOfMultipleReferenceFolders.json");
            rfs = reader.read();

            assertEquals(2, rfs.size());
            ReferenceFolder f1 = rfs.get(0);
            ReferenceImage img1 = folder1.getReferenceImages().get(0);
            ReferenceImage img2 = folder1.getReferenceImages().get(1);
            ReferenceFolder folder2akaSubFolderOfFolder1 = folder1.getSubRefFolders().get(0);

            checkReferenceFolder("Folder 1", 2, 1, f1);
            checkReferenceFolder("Folder 2", 1, 0, folder2akaSubFolderOfFolder1);
            checkReferenceImage("Image 1", "src/main/resources/artref-add.png", img1);
            checkReferenceImage("Image 2", "src/main/resources/artref-addcp.png", img2);

            ReferenceFolder f3 = rfs.get(1);
            ReferenceImage img4 = folder3.getReferenceImages().get(0);

            checkReferenceImage("Image 4", "src/main/resources/artref-bg.png", img4);
            checkReferenceFolder("Folder 3", 1, 0, f3);
        } catch (IOException e) {
            fail("IOException unexpected!");
        }
    }
}
