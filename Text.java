import greenfoot.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * An object whose image is a single horizontal line of characters created from
 * a string, meant to be read by the user.
 * <p>
 * Each character is rendered with its corresponding image from the characters
 * image subdirectory. Note that this class's charmap only contains ASCII
 * characters from 0x20 to 0x7E. If any other character is encountered when
 * rendering text, an {@link IndexOutOfBoundsException} will be thrown.
 * <p>
 * Text objects can be constructed with a string or an integer which is
 * automatically turned into a string using {@link String#valueOf(int)}.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class Text extends PixelActor {
    /**
     * The height of character images found in ./images/characters/.
     * Text rendering assumes all characters have this height.
     */
    public static final int CHARACTER_HEIGHT = 10;

    /**
     * The number of pixels to leave between characters in text.
     */
    public static final int CHARACTER_SPACING = 1;

    // Map characters to their image representations
    // A character's image is found at the index of the ASCII value minus 0x20 so that it starts at space
    private static final GreenfootImage[] charmap;
    static {
        charmap = new GreenfootImage[0x7F - 0x20];
        int i = 0;
        for (String entity : new String[] {"space", "excl", "quot", "num", "dollar", "percnt", "amp", "apos", "lpar", "rpar", "ast", "plus", "comma", "minus", "period", "sol"}) {
            charmap[i++] = new GreenfootImage("characters/" + entity + ".png");
        }
        for (int n = 0; n <= 9; n++) {
            charmap[i++] = new GreenfootImage("characters/" + n + ".png");
        }
        for (String entity : new String[] {"colon", "semi", "lt", "equals", "gt", "quest", "commat"}) {
            charmap[i++] = new GreenfootImage("characters/" + entity + ".png");
        }
        for (char c = 'a'; c <= 'z'; c++) {
            charmap[i++] = new GreenfootImage("characters/" + c + ".png");
        }
        for (String entity : new String[] {"lsqb", "bsol", "rsqb", "hat", "lowbar", "grave"}) {
            charmap[i++] = new GreenfootImage("characters/" + entity + ".png");
        }
        for (char c = 'a'; c <= 'z'; c++) {
            charmap[i++] = new GreenfootImage("characters/" + c + "low.png");
        }
        for (String entity : new String[] {"lcub", "verbar", "rcub", "tilde"}) {
            charmap[i++] = new GreenfootImage("characters/" + entity + ".png");
        }
    }

    /**
     * Options for horizontal text alignment.
     * <p>
     * These define where a text object's position is located relative to the
     * text image.
     * <ul>
     * <li>{@code LEFT}: The actor position is found at the left edge. Text extends to the right from there.
     * <li>{@code RIGHT}: The actor position is found at the right edge. Text extends to the left from there.
     * <li>{@code CENTER}: The actor position is found at the horizontal center. Text extends equally left and right from there.
     * </ul>
     */
    public enum AnchorX {
        LEFT, RIGHT, CENTER;
    }

    /**
     * Options for vertical text alignment.
     * <p>
     * These define where a text object's position is located relative to the
     * text image.
     * <ul>
     * <li>{@code TOP}: The actor position is found at the top edge. Text extends downwards from there.
     * <li>{@code BOTTOM}: The actor position is found at the bottom edge. Text extends upwards from there.
     * <li>{@code CENTER}: The actor position is found at the horizontal center. Text extends equally upwards and downwards from there.
     * </ul>
     */
    public enum AnchorY {
        TOP, BOTTOM, CENTER;
    }

    private final AnchorX anchorX;
    private final AnchorY anchorY;

    /**
     * Creates a displayable text object from the given string with the
     * specified alignment.
     *
     * @param content the String to render to this text object
     * @param anchorX a {@link AnchorX} value describing horizontal alignment
     * @param anchorY a {@link AnchorY} value describing vertical alignment
     */
    public Text(String content, AnchorX anchorX, AnchorY anchorY) {
        super(createStringImage(content));
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        updatePosition();
    }

    /**
     * Creates a displayable text object from the given integer with the
     * specified alignment.
     * <p>
     * This is identical to passing {@link String#valueOf(value)} as the content to {@link #Text(String, AnchorX, AnchorY)}.
     *
     * @param value the integer to render to this text object, using a base 10 representation
     * @param anchorX a {@link AnchorX} value describing horizontal alignment
     * @param anchorY a {@link AnchorY} value describing vertical alignment
     */
    public Text(int value, AnchorX anchorX, AnchorY anchorY) {
        this(String.valueOf(value), anchorX, anchorY);
    }

    /**
     * Updates this text object's image to display the given string.
     *
     * @param content the string to render to this text object
     */
    public void setContent(String content) {
        setImage(createStringImage(content));
        updatePosition();
    }

    /**
     * Updates this text object's image to display the given integer.
     * <p>
     * This is identical to passing {@link String#valueOf(value)} to {@link #setContent(String)}.
     *
     * @param value the integer to render to this text object, using a base 10 representation
     */
    public void setContent(int value) {
        setContent(String.valueOf(value));
    }

    /**
     * Updates this text object's center of rotation so it is properly aligned
     * in accordance with its AnchorX and AnchorY parameters.
     */
    private void updatePosition() {
        int x = 0;
        int y = 0;
        switch (anchorX) {
        case RIGHT:
            x = getOriginalImage().getWidth() - 1;
            break;
        case CENTER:
            x = getOriginalImage().getWidth() / 2;
            break;
        }
        switch (anchorY) {
        case BOTTOM:
            y = getOriginalImage().getHeight() - 1;
            break;
        case CENTER:
            y = getOriginalImage().getHeight() / 2;
            break;
        }
        setCenterOfRotation(x, y);
    }

    /**
     * Creates an image with a readable representation the given string.
     * <p>
     * If content is an empty string, this method returns {@code null}.
     * <p>
     * Each character in the string is drawn using its defined image
     * representation one after another on the x-axis.
     *
     * @param content the string to render to an image
     * @return a new {@link GreenfootImage} containing a representation of the given content
     */
    public static GreenfootImage createStringImage(String content) {
        if (content == null) {
            throw new IllegalArgumentException("String content must not be null");
        } else if (content.length() < 1) {
            return null;
        }
        // Find the dimensions and character images required for this text
        int width = -CHARACTER_SPACING;
        GreenfootImage[] charImages = new GreenfootImage[content.length()];
        for (int i = 0; i < content.length(); i++) {
            GreenfootImage charImage = charmap[content.charAt(i) - ' '];
            if (charImage.getHeight() != CHARACTER_HEIGHT) {
                throw new UnsupportedOperationException("Image for character '" + content.charAt(i) + "' has a height that does not match Text.CHARACTER_HEIGHT");
            }
            width += charImage.getWidth() + CHARACTER_SPACING;
            charImages[i] = charImage;
        }
        // Draw the characters to an image
        GreenfootImage result = new GreenfootImage(width, CHARACTER_HEIGHT);
        for (int i = 0, x = 0; i < charImages.length; x += charImages[i].getWidth() + CHARACTER_SPACING, i++) {
            result.drawImage(charImages[i], x, 0);
        }
        return result;
    }
}
