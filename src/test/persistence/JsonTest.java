package persistence;

import model.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
