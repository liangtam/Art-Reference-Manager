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
    public void testAddColourOneColour(){
        boolean addColourSuccess = sunsetColourPalette.addColour(colour1);
        int numOfColours = sunsetColourPalette.getNumOfColours();

        List<Colour> colours = sunsetColourPalette.getColours();
        assertEquals(1, numOfColours);
        assertTrue(addColourSuccess);
        assertEquals(colour1, colours.get(0));
    }

    @Test
    public void testAddColourSameColourMultipleTimes() {
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
    public void testAddColourThreeDifferentColours() {
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
    // tests for addColourPalette
    @Test
    public void testAddColourPaletteOneColourPalette() {
        boolean addSubColourPaletteSuccess = sunsetColourPalette.addSubColourPalette(oceanColourPalette);
        assertTrue(addSubColourPaletteSuccess);
        assertEquals(1, sunsetColourPalette.getNumOfColourPalettes());

    }

    @Test
    public void testAddColourPalettesMultipleDifferentPalettes(){
        boolean addSubColourPalette2 = sunsetColourPalette.addSubColourPalette(oceanColourPalette);
        boolean addSubColourPalette3 = sunsetColourPalette.addSubColourPalette(warmColourPalette);

        assertTrue(addSubColourPalette2);
        assertTrue(addSubColourPalette3);

        assertEquals(2, sunsetColourPalette.getNumOfColourPalettes());
        assertEquals(oceanColourPalette, sunsetColourPalette.getSubColourPalettes().get(0));
        assertEquals(warmColourPalette, sunsetColourPalette.getSubColourPalettes().get(1));
    }

    @Test
    public void testAddColourPaletteSamePaletteMultipleTimes() {
        boolean addSubColourPalette2 = sunsetColourPalette.addSubColourPalette(oceanColourPalette);
        boolean addSubColourPalette2Again = sunsetColourPalette.addSubColourPalette(oceanColourPalette);

        assertTrue(addSubColourPalette2);
        assertFalse(addSubColourPalette2Again);
        int numOfColourPalettes = sunsetColourPalette.getNumOfColourPalettes();
        assertEquals(1, numOfColourPalettes);
        assertTrue(sunsetColourPalette.getSubColourPalettes().contains(oceanColourPalette));
    }

    // test for deleteSubColourPalette
    @Test
    public void testDeleteSubColourPaletteExistingSubColourPalette() {
        sunsetColourPalette.addSubColourPalette(warmColourPalette);
        sunsetColourPalette.addSubColourPalette(oceanColourPalette);

        boolean delWarmColoursSuccess = sunsetColourPalette.deleteSubColourPalette(warmColourPalette);
        assertTrue(delWarmColoursSuccess);

        int numOfColourPalettes = sunsetColourPalette.getNumOfColourPalettes();
        assertEquals(1, numOfColourPalettes);
        assertEquals(oceanColourPalette, sunsetColourPalette.getSubColourPalettes().get(0));
    }

    @Test
    public void testDeleteSubColourPaletteNonexistentSubColourPalette() {
        sunsetColourPalette.addSubColourPalette(warmColourPalette);

        boolean delOceanColourSuccess = sunsetColourPalette.deleteSubColourPalette(oceanColourPalette);
        assertFalse(delOceanColourSuccess);
        assertEquals(1, sunsetColourPalette.getNumOfColourPalettes());
        assertTrue(sunsetColourPalette.getSubColourPalettes().contains(warmColourPalette));
    }

    // tests for deleteColour
    @Test
    public void testDeleteColourExistingColour() {
        sunsetColourPalette.addColour(colour1);
        sunsetColourPalette.addColour(colour2);

        boolean delColour1Success = sunsetColourPalette.deleteColour(colour1.getName());
        assertTrue(delColour1Success);
        int numOfColours = sunsetColourPalette.getNumOfColours();
        assertEquals(1, numOfColours);
        assertEquals(colour2, sunsetColourPalette.getColours().get(0));
    }

    @Test
    public void testDeleteColourNonexistentColour() {
        boolean delColour2Success = sunsetColourPalette.deleteColour(colour2.getName());
        assertFalse(delColour2Success);
        assertEquals(0, sunsetColourPalette.getNumOfColours());
        sunsetColourPalette.addColour(colour3);

        boolean delColour1Success = sunsetColourPalette.deleteColour(colour1.getName());
        assertFalse(delColour1Success);

        assertEquals(1, sunsetColourPalette.getNumOfColours());
        assertEquals(colour3, sunsetColourPalette.getColours().get(0));

    }

    // other tests
    @Test
    public void testIfSubColourPaletteAlreadyExists() {
        sunsetColourPalette.addSubColourPalette(warmColourPalette);

        boolean warmCPExists = sunsetColourPalette.ifSubColourPaletteAlreadyExists(warmColourPalette.getName());
        boolean oceanCPExists = sunsetColourPalette.ifSubColourPaletteAlreadyExists(oceanColourPalette.getName());

        assertTrue(warmCPExists);
        assertFalse(oceanCPExists);
    }

    @Test
    public void testSetName() {
        sunsetColourPalette.setName("Sunset but cooler");
        assertEquals("Sunset but cooler", sunsetColourPalette.getName());
    }

    // JSON tests
    @Test
    public void testToJson() {

    }
}
