import greenfoot.*;

/**
 * A Text Actor that can be animated character by character.
 * <p>Call {@link updateImage} in the act of the world to advance the animation.</p>
 * <p>Use "\n" to create a new line.</p>
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class AnimatedText extends Text {
    private String fullContent;
    private Timer nextCharTimer;
    private int currentIndex;

    /**
     * Create a text object that will be animated character by character.
     *
     * @param content The full content of the Text
     * @param anchorX A {@link AnchorX} value describing horizontal alignment
     * @param anchorY A {@link AnchorY} value describing vertical alignment
     */
    public AnimatedText(String content, AnchorX anchorX, AnchorY anchorY) {
        super(content.charAt(0), anchorX, anchorY);
        fullContent = content;
        nextCharTimer = new Timer(2);
        currentIndex = 1;
    }

    public void updateImage() {
        if (currentIndex == fullContent.length()) return;

        if (nextCharTimer.ended()) {
            currentIndex++;
            setContent(fullContent.substring(0, currentIndex));
            nextCharTimer.restart();
        }
    }
}
