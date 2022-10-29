package persistence;

import model.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
