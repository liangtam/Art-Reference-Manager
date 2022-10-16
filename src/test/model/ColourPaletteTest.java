package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ColourPaletteTest {
    private Colour colour1;
    private Colour colour2;
    private Colour colour3;
    private ColourPalette sunsetColourPalette;
    private ColourPalette oceanColourPalette;
    private ColourPalette warmColourPalette;

    @BeforeEach
    public void setUp() {
        colour1 = new Colour("Red", "#FF0000");
        colour2 = new Colour("Green", "#00FF00");
        colour3 = new Colour("Sky Blue", "#87CEEB");
        sunsetColourPalette = new ColourPalette("Sunset Colours");
        oceanColourPalette = new ColourPalette("Ocean Colours");
        warmColourPalette = new ColourPalette("Warm Colours");
    }

    // tests for addColour
    @Test
    public void testAddOneColour(){
        boolean addColourSuccess = sunsetColourPalette.addColour(colour1);
        int numOfColours = sunsetColourPalette.getNumOfColours();

        List<Colour> colours = sunsetColourPalette.getColours();
        assertEquals(1, numOfColours);
        assertTrue(addColourSuccess);
        assertEquals(colour1, colours.get(0));
    }

    @Test
    public void testAddSameColourMultipleTimes() {
        boolean addColour2Success = sunsetColourPalette.addColour(colour2);
        boolean addColour2AgainSuccess = sunsetColourPalette.addColour(colour2);

        assertTrue(addColour2Success);
        assertFalse(addColour2AgainSuccess);

        int numOfColours = sunsetColourPalette.getNumOfColours();
        List<Colour> colours = sunsetColourPalette.getColours();
        assertEquals(1, numOfColours);
        assertEquals(colour2, colours.get(0));

    }

    @Test
    public void testAddThreeDifferentColours() {
        boolean addColour1Success = sunsetColourPalette.addColour(colour1);
        boolean addColour2Success = sunsetColourPalette.addColour(colour2);
        boolean addColour3Success = sunsetColourPalette.addColour(colour3);
        int numOfColours = sunsetColourPalette.getNumOfColours();

        assertTrue(addColour1Success);
        assertTrue(addColour2Success);
        assertTrue(addColour3Success);
        assertEquals(3, numOfColours);

        List<Colour> colours = sunsetColourPalette.getColours();
        assertEquals(colour1, colours.get(0));
        assertEquals(colour2, colours.get(1));
        assertEquals(colour3, colours.get(2));
    }

    @Test
    public void testDeleteAColour() {
        sunsetColourPalette.addColour(colour1);
        sunsetColourPalette.addColour(colour2);

        boolean delColour1Success = sunsetColourPalette.deleteColour(colour1);
        assertTrue(delColour1Success);
        int numOfColours = sunsetColourPalette.getNumOfColours();
        assertEquals(1, numOfColours);
        assertEquals(colour2, sunsetColourPalette.getColours().get(0));
    }

    // tests for addColourPalette
    @Test
    public void testAddOneColourPalette() {
        boolean addSubColourPaletteSuccess = sunsetColourPalette.addSubColourPalette(oceanColourPalette);
        assertTrue(addSubColourPaletteSuccess);
        assertEquals(1, sunsetColourPalette.getNumOfColourPalettes());

    }

    @Test
    public void testAddMultipleDifferentColourPalettes(){
        boolean addSubColourPalette2 = sunsetColourPalette.addSubColourPalette(oceanColourPalette);
        boolean addSubColourPalette3 = sunsetColourPalette.addSubColourPalette(warmColourPalette);

        assertTrue(addSubColourPalette2);
        assertTrue(addSubColourPalette3);

        assertEquals(2, sunsetColourPalette.getNumOfColourPalettes());
        assertEquals(oceanColourPalette, sunsetColourPalette.getSubColourPalettes().get(0));
        assertEquals(warmColourPalette, sunsetColourPalette.getSubColourPalettes().get(1));
    }

    @Test
    public void testAddSameColourPaletteMultipleTimes() {
        boolean addSubColourPalette2 = sunsetColourPalette.addSubColourPalette(oceanColourPalette);
        boolean addSubColourPalette2Again = sunsetColourPalette.addSubColourPalette(oceanColourPalette);

        assertTrue(addSubColourPalette2);
        assertFalse(addSubColourPalette2Again);
        int numOfColourPalettes = sunsetColourPalette.getNumOfColourPalettes();
        assertEquals(1, numOfColourPalettes);
        assertTrue(sunsetColourPalette.getSubColourPalettes().contains(oceanColourPalette));
    }

    @Test
    public void testDeleteAnExistingSubColourPalette() {
        sunsetColourPalette.addSubColourPalette(warmColourPalette);
        sunsetColourPalette.addSubColourPalette(oceanColourPalette);

        boolean delWarmColoursSuccess = sunsetColourPalette.deleteSubColourPalette(warmColourPalette);
        assertTrue(delWarmColoursSuccess);
        int numOfColourPalettes = sunsetColourPalette.getNumOfColourPalettes();
        assertEquals(1, numOfColourPalettes);
        assertEquals(oceanColourPalette, sunsetColourPalette.getSubColourPalettes().get(0));
    }

    // other tests
    @Test
    public void testIfSubColourPaletteAlreadyExists() {
        sunsetColourPalette.addColour(colour1);
        sunsetColourPalette.addColour(colour2);

        boolean colour1Exists = sunsetColourPalette.ifColourAlreadyExists(colour1);
        boolean colour3Exists = sunsetColourPalette.ifColourAlreadyExists(colour3);

        assertTrue(colour1Exists);
        assertFalse(colour3Exists);
    }
}
