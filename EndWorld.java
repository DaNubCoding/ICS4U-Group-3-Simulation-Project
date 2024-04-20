import greenfoot.*;

/**
 * A world where the user is informed of how the simulation ended.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class EndWorld extends PixelWorld {
    /**
     * Creates a new end world to display a specific ending depending on the
     * given end state.
     *
     * @param state an {@link EndState} value for the end state to display
     */
    public EndWorld(SimulationWorld simWorld, EndState state) {
        super(250, 160);

        addObject(new AnimatedText(state.description, Text.AnchorX.LEFT, Text.AnchorY.TOP, 100), 16, 16);
        addObject(new Button("Continue", () -> Greenfoot.setWorld(new SummaryWorld(simWorld))), getWidth() - 38, getHeight() - 16);

        render();
    }

    @Override
    public void act() {
        render();
        Timer.incrementAct();
    }

    /**
     * Updates the display image of this world.
     */
    private void render() {
        GreenfootImage canvas = getCanvas();
        canvas.setColor(Color.WHITE);
        canvas.fill();
        canvas.setColor(new Color(130, 210, 220));
        canvas.fillRect(16, 16, 100, getHeight());
        renderPixelActors();
        updateImage();
    }
}
