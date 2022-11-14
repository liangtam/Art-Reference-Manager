package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReferenceImageTest {
    private ReferenceImage ref1;

    @BeforeEach
    public void setUp() {
        ref1 = new ReferenceImage("Ref1",
                "src/main/resources/artref-add.png");
    }

    @Test
    public void testCreateImageFile() {
        ref1.createImage();
        BufferedImage image = ref1.getImage();

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File
                    ("C:\\Users\\tammi\\IdeaProjects\\CPSC210\\Project\\project_c1g1c"
                            +"\\src\\main\\resources\\artref-add.png"));
        } catch (IOException e) {
            System.out.println("Image doesn't exist!");
        }

        assertEquals(img.getHeight(), image.getHeight());
        assertEquals(img.getColorModel(), image.getColorModel());
        assertEquals(img.getPropertyNames(), image.getPropertyNames());
    }

    @Test
    public void testCreateImageFileNullfile() {
        ReferenceImage ref2 = new ReferenceImage("Ref2", "idontexist.png");
        ref2.createImage();
        assertEquals(null, ref2.getImage());
    }

    @Test
    public void testSetName() {
        ref1.setName("Reference 1");
        assertEquals("Reference 1", ref1.getName());
    }

    @Test
    public void testSetImageURL() {
        ref1.setImageURL("src/main/resources/artref-bg.png");
        assertEquals("src/main/resources/artref-bg.png", ref1.getImageURL());
    }

    @Test
    public void testToJson() {
        JSONObject ref1JsonObj = ref1.toJson();
        assertEquals("Ref1", ref1JsonObj.getString("imageName"));
        assertEquals("src/main/resources/artref-add.png", ref1JsonObj.getString("imageURL"));
    }
}
