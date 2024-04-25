import greenfoot.GreenfootSound;

/**
 * Handling for world-independent looping background music.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public final class Music {
    // Don't let anyone instantiate this class
    private Music() {}

    // The current sound used as background music
    private static GreenfootSound sound = null;

    /**
     * Set the current background music to the given sound. Any currently
     * playing background music will be stopped. The next call to {@link #play}
     * will play the given sound on loop from the beginning.
     *
     * @param sound a GreenfootSound to play as background music
     */
    public static void set(GreenfootSound newSound) {
        if (sound != null) {
            sound.stop();
        }
        sound = newSound;
        sound.stop();
    }

    /**
     * Continue playing the current background music, if any.
     */
    public static void play() {
        if (sound == null) {
            return;
        }
        sound.playLoop();
    }

    /**
     * Pause the currently playing background music, if any.
     */
    public static void pause() {
        if (sound == null) {
            return;
        }
        sound.pause();
    }

    /**
     * Stop playing the current background music, if any, and unset the sound
     * used so that future attempts to play do nothing.
     */
    public static void stop() {
        if (sound == null) {
            return;
        }
        sound.stop();
        sound = null;
    }
}
