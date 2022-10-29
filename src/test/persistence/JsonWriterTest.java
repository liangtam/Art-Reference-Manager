package persistence;

import model.Colour;
import model.ColourPalette;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
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
}
