package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

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
}