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
     *
     * @author Martin Baldwin
     * @version April 2024
     */
    public static enum Voice {
        LOW(4), HIGH(4);

        private final String[] soundPaths;

        private Voice(int variationCount) {
            soundPaths = new String[variationCount];
            for (int i = 0; i < soundPaths.length; i++) {
                soundPaths[i] = "voices/" + name().toLowerCase() + "_" + i + ".wav";
            }
        }
    }

    /**
     * A collection of sound effects defined by a {@link Voice} that can be
     * played randomly as text is displayed.
     *
     * @author Martin Baldwin
     * @version April 2024
     */
    private static class VoiceSound {
        private final SoundEffect[] sounds;
        private int lastSoundIndex;

        public VoiceSound(Voice voice) {
            sounds = new SoundEffect[voice.soundPaths.length];
            for (int i = 0; i < sounds.length; i++) {
                sounds[i] = new SoundEffect(voice.soundPaths[i], 1);
            }
            lastSoundIndex = 0;
        }

        /**
         * Plays a random sound effect of this voice, stopping the previous one.
         */
        private void play() {
            sounds[lastSoundIndex].stop();
            lastSoundIndex = Util.randInt(0, sounds.length - 1);
            sounds[lastSoundIndex].play();
        }
    }

    private String fullContent;
    private Timer nextCharTimer;
    private int currentIndex;
    // Sound effects to play as the text is displayed
    private VoiceSound voice;
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
        if (voice == null) {
            this.voice = null;
        } else {
            this.voice = new VoiceSound(voice);
        }
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
            voice.play();
        }
    }
}
