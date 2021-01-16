import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageTest {

    @Test
    public void testLoadImage() {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif")));
            assertNotNull(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
