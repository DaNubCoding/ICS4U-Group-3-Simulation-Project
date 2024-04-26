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

    private static double fadePercentage = 1.0;
    private static double fadeSpeed;

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

    public static void doFade() {
        fadePercentage += fadeSpeed;
        fadePercentage = Math.max(Math.min(fadePercentage, 1.0), 0.0);
        // The music default volume seems to be at around 75
        // Greenfoot has a bug where the volume is not set correctly on initialization
        sound.setVolume((int) (fadePercentage * 75));
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

    /**
     * Fade in the music at the given speed.
     *
     * @param speed the speed at which to fade in the music
     */
    public static void triggerFadeIn(double speed) {
        fadePercentage = 0.0;
        fadeSpeed = speed;
    }

    /**
     * Fade out the music at the given speed.
     *
     * @param speed the speed at which to fade out the music
     */
    public static void triggerFadeOut(double speed) {
        fadePercentage = 1.0;
        fadeSpeed = -speed;
    }
}
