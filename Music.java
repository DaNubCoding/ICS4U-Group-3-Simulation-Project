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
     * Pause the currently playing background music, if any.
     */
    public static void pause() {
        if (sound == null) {
            return;
        }
        sound.pause();
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
     * Begin playing the given sound on loop.
     *
     * @param sound a GreenfootSound to play as background music
     */
    public static void play(GreenfootSound sound) {
        Music.sound = sound;
        play();
    }

    /**
     * Begin playing the sound using the specified file path on loop.
     *
     * @param soundPath the file path of a sound file to play as background music
     */
    public static void play(String soundPath) {
        play(new GreenfootSound(soundPath));
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
