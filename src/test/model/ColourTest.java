package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the methods in Colour class
class ColourTest {
    private Colour red;

    @BeforeEach
    public void setUp() {
        red = new Colour("Red", "#FF0000");
    }

    @Test
    public void testCreateColourRep() {
        red.createColourVisual(red.getHex());
        Color colourVisual = red.getColourVisual();
        double r = colourVisual.getRed();
        double g = colourVisual.getGreen();
        double b = colourVisual.getBlue();
        assertEquals(255, r);
        assertEquals(0, g);
        assertEquals(0, b);
    }

    @Test
    public void testSetHex() {
        red.setHex("#FF2500");
        assertEquals("#FF2500", red.getHex());
    }

    @Test
    public void testSetName() {
        red.setName("Warm red");
        assertEquals("Warm red", red.getName());
    }

    @Test
    public void testToJson() {
        JSONObject redJson = red.toJson();
        assertEquals("Red", redJson.get("name"));
        assertEquals("#FF0000", redJson.get("hex"));

    }
}