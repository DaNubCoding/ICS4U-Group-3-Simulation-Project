import greenfoot.*;

/**
 * A Text Actor that can be animated character by character.
 * <p>Call {@link updateImage} in the act of the world to advance the animation.</p>
 * <p>Use "\n" to create a new line.</p>
 *
 * @author Andrew Wang
 * @author Martin Baldwin
 * @version April 2024
 */
public class AnimatedText extends Text {
    public static final int TEXT_SPEED = 2;

    /**
     * Different styles of voice to play as animated text is displayed, each
     * with a set of sound files to randomly play.
     * <p>
     * Note that due to a known bug with the internal audio data lines behind
     * Greenfoot, repeatedly stopping and starting a sound will hang the
     * program (apparently for around 1975 ms each time). Thus, this enum's
     * {@link playSound} method creates a new {@link SoundEffect} each time it
     * is called so that the sound works as intended.
     *
     * @author Martin Baldwin
     * @version April 2024
     */
    public static enum Voice {
        LOW(4), HIGH(4), BEEP(3);

        private final String[] soundPaths;
        private SoundEffect lastSound;

        /**
         * Create a voice with a set number of variations.
         *
         * @param variationCount The number of sound files to randomly play
         */
        private Voice(int variationCount) {
            soundPaths = new String[variationCount];
            for (int i = 0; i < soundPaths.length; i++) {
                soundPaths[i] = "voices/" + name().toLowerCase() + "_" + i + ".wav";
            }
            lastSound = null;
        }

        /**
         * Plays a random sound effect of this voice, stopping the previous one.
         */
        private void playSound() {
            if (lastSound != null) {
                lastSound.stop();
            }
            lastSound = new SoundEffect(soundPaths[Util.randInt(0, soundPaths.length - 1)], 1);
            lastSound.play();
        }
    }

    private String fullContent;
    private Timer nextCharTimer;
    private int currentIndex;
    // Sound effects to play as the text is displayed
    private Voice voice;
    // Never voice consecutive characters (too crazy sounding)
    private boolean voicedLastChar;

    /**
     * Create a text object that will be animated character by character with
     * sound effects.
     *
     * @param content The full content of the Text
     * @param anchorX A {@link AnchorX} value describing horizontal alignment
     * @param anchorY A {@link AnchorY} value describing vertical alignment
     * @param voice A {@link Voice} value setting the sound effects to play along with this text, or {@code null} for no sound
     */
    public AnimatedText(String content, AnchorX anchorX, AnchorY anchorY, Voice voice) {
        super("", anchorX, anchorY);
        fullContent = content;
        nextCharTimer = new Timer(TEXT_SPEED);
        currentIndex = 0;
        this.voice = voice;
        voicedLastChar = false;
    }

    /**
     * Create a text object that will be animated character by character with
     * sound effects, confined to a maximum width.
     *
     * @param content The full content of the Text
     * @param anchorX A {@link AnchorX} value describing horizontal alignment
     * @param anchorY A {@link AnchorY} value describing vertical alignment
     * @param maxWidth The desired maximum width of the rendered content
     * @param voice A {@link Voice} value setting the sound effects to play along with this text, or {@code null} for no sound
     */
    public AnimatedText(String content, AnchorX anchorX, AnchorY anchorY, int maxWidth, Voice voice) {
        this(Text.reflowToWidth(content, maxWidth), anchorX, anchorY, voice);
    }

    /**
     * Create a text object that will be animated character by character.
     *
     * @param content The full content of the Text
     * @param anchorX A {@link AnchorX} value describing horizontal alignment
     * @param anchorY A {@link AnchorY} value describing vertical alignment
     */
    public AnimatedText(String content, AnchorX anchorX, AnchorY anchorY) {
        this(content, anchorX, anchorY, null);
    }

    /**
     * Create a text object that will be animated character by character,
     * confined to a maximum width.
     *
     * @param content The full content of the Text
     * @param anchorX A {@link AnchorX} value describing horizontal alignment
     * @param anchorY A {@link AnchorY} value describing vertical alignment
     * @param maxWidth The desired maximum width of the rendered content
     */
    public AnimatedText(String content, AnchorX anchorX, AnchorY anchorY, int maxWidth) {
        this(content, anchorX, anchorY, maxWidth, null);
    }

    @Override
    public void act() {
        updateImage();
    }

    /**
     * Update the image of the text, advancing the animation by one character when necessary.
     */
    public void updateImage() {
        if (currentIndex == fullContent.length() || !nextCharTimer.ended()) return;

        // Move to the next character
        currentIndex++;
        setContent(fullContent.substring(0, currentIndex));
        nextCharTimer.restart();

        if (voice == null) {
            return;
        }

        // Play a random sound effect for the voice, only for non-space
        // characters when the last character wasn't voiced already
        voicedLastChar = !voicedLastChar && fullContent.charAt(currentIndex - 1) != ' ';
        if (voicedLastChar) {
            voice.playSound();
        }
    }
}
