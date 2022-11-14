package persistence;

import model.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

// **based on JsonSerializationDemo project**

// Used to check the accuracy of the fields of a Colour and fields of a ColourPalette
public class JsonTest {
    protected void checkColour(String name, String hex, Color color, Colour colour) {
        assertEquals(name, colour.getName());
        assertEquals(hex, colour.getHex());
        assertEquals(color, colour.getColourVisual());

    }

    protected void checkColourPalette(String name, int numOfColours, int numOfSubPalettes, ColourPalette cp) {
        assertEquals(name, cp.getName());
        assertEquals(numOfColours, cp.getNumOfColours());
        assertEquals(numOfSubPalettes, cp.getNumOfColourPalettes());
    }

    protected void checkReferenceImage(String name, String imageURL, ReferenceImage refImage) {
        assertEquals(name, refImage.getName());
        assertEquals(imageURL, refImage.getImageURL());
    }

    protected void checkReferenceFolder(String name, int numOfRefImages, int numOfSubFolders, ReferenceFolder rf) {
        assertEquals(name, rf.getFolderName());
        assertEquals(numOfRefImages, rf.getReferenceImages().size());
        assertEquals(numOfSubFolders, rf.getSubRefFolders().size());
    }
}
