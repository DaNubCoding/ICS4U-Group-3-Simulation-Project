import greenfoot.*;

/**
 * A timer object that keeps track of a set amount of time.
 * <p>Intended use:</p>
 * <pre>
 * if (timer.ended()) {
 *     // Do things
 *     timer.restart();
 * }
 * </pre>
 * 
 * @author Andrew Wang
 * @version April 2024
 */
public class Timer  
{
    // The frame at which the timer begins
    public long startFrame;
    // The total number of frames before the timer ends
    public int totalFrames;
    
    /**
     * Initialize the Timer with a set number of frames before it ends.
     * 
     * @param totalFrames The total number of frames before the timer ends
     */
    public Timer(int totalFrames) {
        this.startFrame = PixelWorld.getCurrentAct();
        this.totalFrames = totalFrames;
    }
    
    /**
     * Check if the timer has ended
     * (whether it has reached the specified number of frames).
     * 
     * @return True if ended, false otherwise
     */
    public boolean ended() {
        return PixelWorld.getCurrentAct() - startFrame > totalFrames;
    }
    
    /**
     * Get the progress of the timer as a percentage (i.e. 20% would be 0.2).
     * 
     * @return The progress as a percentage
     */
    public double progress() {
        return (double) (PixelWorld.getCurrentAct() - startFrame) / totalFrames;
    }
    
    /**
     * Call if you want to restart the timer once it ends, creating a looping timer.
     */
    public void restart() {
        startFrame = PixelWorld.getCurrentAct();
    }
    
    /**
     * Restart the timer with a new total number of frames before it ends.
     * 
     * @param newTotalFrames The number of frames before the timer ends again
     */
    public void restart(int newTotalFrames) {
        startFrame = PixelWorld.getCurrentAct();
        totalFrames = newTotalFrames;
    }
}