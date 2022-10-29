package persistence;

import model.Colour;
import model.ColourPalette;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
}
