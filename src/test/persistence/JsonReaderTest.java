package persistence;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.*;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/iDon'tExist.json");
        try {
            List<ColourPalette> colourPalettes = reader.read();
            fail("IOException expected. You shouldn't have gotten here!");
        } catch (IOException e) {

        }
    }

    @Test
    public void testReaderEmptyFile() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyListOfColourPalettes.json");
        try {
            List<ColourPalette> colourPalettes = reader.read();
            assertEquals(0, colourPalettes.size());
        } catch (IOException e) {
            fail("Shouldn't have been not able to read file!");
        }
    }

    @Test
    public void testReaderListOfOneColourPalette() {
        JsonReader reader = new JsonReader("./data/testReaderListOfOneColourPalette.json");
        try {
            List<ColourPalette> colourPalettes = reader.read();
            assertEquals(1, colourPalettes.size());
            ColourPalette cp = colourPalettes.get(0);
            checkColourPalette("Colour Palette 1", 0, 0 , cp);
        } catch (IOException e) {
            fail("Shouldn't have been not able to read file!");
        }
    }

    @Test
    public void testReaderListOfMultipleColourPalettes() {
        JsonReader reader = new JsonReader("./data/testReaderListOfMultipleColourPalettes.json");
        try {
            List<ColourPalette> colourPalettes = reader.read();
            assertEquals(2, colourPalettes.size());
            ColourPalette sunsetCP = colourPalettes.get(0);
            Colour sunsetColorRed = sunsetCP.getColours().get(0);
            Color red = new Color(255, 0, 0);
            Colour sunsetColorOrange = sunsetCP.getColours().get(1);
            Color redOrange = new Color(255, 37, 0);
            ColourPalette sunsetSubCP = sunsetCP.getSubColourPalettes().get(0);

            checkColourPalette("Sunset", 2, 1, sunsetCP);
            checkColourPalette("Sky", 1, 0, sunsetSubCP);
            checkColour("Red Orange", "#FF2500", redOrange, sunsetColorOrange);
            checkColour("Red", "#FF0000", red, sunsetColorRed);

            ColourPalette oceanCP = colourPalettes.get(1);
            Colour oceanColorBlue = oceanCP.getColours().get(0);
            Color blue = new Color(0,0,255);

            checkColour("Blue", "#0000FF", blue, oceanColorBlue);
            checkColourPalette("Ocean", 1, 0, oceanCP);
        } catch (IOException e) {
            fail("Shouldn't have been not able to read file!");
        }
    }
}
