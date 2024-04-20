import greenfoot.*;

/**
 * An object whose image consists of horizontal lines of characters created from
 * a string, meant to be read by the user.
 * <p>
 * Each character is rendered with its corresponding image from the characters
 * image subdirectory. Note that this class's charmap only contains ASCII
 * characters from 0x20 to 0x7E, but the newline character '\n' 0x0A is also
 * handled, shifting all following characters to be drawn on a new line below,
 * starting from the left. If any other character is encountered when rendering
 * text, an {@link IndexOutOfBoundsException} will be thrown.
 *
 * @author Martin Baldwin
 * @author Andrew Wang
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

    /**
     * The number of pixels bewteen lines of the text.
     */
    public static final int LINE_SPACING = 1;

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
     * @param content the string to render to this text object
     * @param anchorX a {@link AnchorX} value describing horizontal alignment
     * @param anchorY a {@link AnchorY} value describing vertical alignment
     */
    public Text(String content, AnchorX anchorX, AnchorY anchorY) {
        super(createStringImage(content), Layer.UI);
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        updatePosition();
    }

    /**
     * Creates a displayable text object from the given string with the
     * specified alignment and desired maximum render width.
     *
     * @param content the string to render to this text object
     * @param anchorX a {@link AnchorX} value describing horizontal alignment
     * @param anchorY a {@link AnchorY} value describing vertical alignment
     * @param maxWidth the desired maximum width of the rendered content
     * @see #reflowToWidth
     */
    public Text(String content, AnchorX anchorX, AnchorY anchorY, int maxWidth) {
        this(reflowToWidth(content, maxWidth), anchorX, anchorY);
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
        int maxWidth = -CHARACTER_SPACING;
        int width = -CHARACTER_SPACING;
        int height = CHARACTER_HEIGHT;
        GreenfootImage[] charImages = new GreenfootImage[content.length()];
        for (int i = 0; i < content.length(); i++) {
            // Move on to the next line if a newline character is found
            if (content.charAt(i) == '\n') {
                width = -CHARACTER_SPACING;
                height += CHARACTER_HEIGHT + LINE_SPACING;
                charImages[i] = null;
                continue;
            }
            GreenfootImage charImage = charmap[content.charAt(i) - ' '];
            if (charImage.getHeight() != CHARACTER_HEIGHT) {
                throw new UnsupportedOperationException("Image for character '" + content.charAt(i) + "' has a height that does not match Text.CHARACTER_HEIGHT");
            }
            width += charImage.getWidth() + CHARACTER_SPACING;
            maxWidth = Math.max(maxWidth, width);
            charImages[i] = charImage;
        }
        if (maxWidth <= 0) {
            // Text consists of only newlines
            return null;
        }
        // Draw the characters to an image
        GreenfootImage result = new GreenfootImage(maxWidth, height);
        for (int i = 0, x = 0, y = 0; i < charImages.length; i++) {
            // A new line is reached
            if (charImages[i] == null) {
                x = 0;
                y += CHARACTER_HEIGHT + LINE_SPACING;
                continue;
            }
            result.drawImage(charImages[i], x, y);
            x += charImages[i].getWidth() + CHARACTER_SPACING;
        }
        return result;
    }

    /**
     * Replaces spaces with newline characters in appropriate places to fit the
     * given content string within the given width when rendered.
     * <p>
     * This method does not handle splitting words. If a single word would
     * appear longer than the maximum width, it will appear on its own line and
     * flow past the maxmimum width. (A word here is a sequence of characters
     * that are not spaces.)
     *
     * @param content the string to reflow
     * @param maxWidth the desired maximum width of the rendered content
     * @return a new string with spaces replaced with newline characters where appropriate
     */
    public static String reflowToWidth(String content, int maxWidth) {
        int width = -CHARACTER_SPACING;
        int wordWidth = -CHARACTER_SPACING;
        int lastSpace = -1;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            result.append(c);
            // Force move to a new line
            if (c == '\n') {
                width = -CHARACTER_SPACING;
                wordWidth = -CHARACTER_SPACING;
                continue;
            }
            int charWidth = charmap[c - ' '].getWidth() + CHARACTER_SPACING;
            width += charWidth;
            wordWidth += charWidth;
            if (c == ' ') {
                lastSpace = result.length() - 1;
                wordWidth = -CHARACTER_SPACING;
            }
            if (width > maxWidth) {
                // Replace the last space with a newline to move the current word to a new line
                if (lastSpace != -1) {
                    result.deleteCharAt(lastSpace);
                    result.insert(lastSpace, '\n');
                }
                width = wordWidth;
            }
        }
        return result.toString();
    }
}
