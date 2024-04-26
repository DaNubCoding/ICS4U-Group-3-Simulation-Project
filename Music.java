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

    // Whether or not to be handling the background music, corresponding to
    // whether or not the program is running.
    private static boolean isRunning = false;

    /**
     * Set the current background music to the given sound file. Any currently
     * playing background music will be stopped. If the program is running, the
     * sound will automatically be started.
     *
     * @param soundPath the file path of a sound file to play as background music
     */
    public static void set(String soundPath) {
        if (sound != null) {
            sound.stop();
        }
        sound = new GreenfootSound(soundPath);
        if (sound == null) {
            return;
        }
        sound.stop();
        if (isRunning) {
            sound.playLoop();
        }
    }

    /**
     * Continue playing the current background music, if any.
     */
    public static void play() {
        isRunning = true;
        if (sound == null) {
            return;
        }
        sound.playLoop();
    }

    /**
     * Pause the currently playing background music, if any.
     */
    public static void pause() {
        isRunning = false;
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
