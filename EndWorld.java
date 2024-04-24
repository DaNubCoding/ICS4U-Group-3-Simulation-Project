import greenfoot.*;

/**
 * A world where the user is informed of how the simulation ended.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class EndWorld extends PixelWorld {
    private SimulationWorld simulationWorld;

    /**
     * Creates a new end world to display a specific ending depending on the
     * given end state.
     *
     * @param simWorld the SimulationWorld object that led to this EndWorld
     * @param state an {@link EndState} value for the end state to display
     */
    public EndWorld(SimulationWorld simWorld, EndState state) {
        super(250, 160);
        simulationWorld = simWorld;

        addObject(new AnimatedText(state.description, Text.AnchorX.LEFT, Text.AnchorY.TOP, 100), 16, 16);
        addObject(new Button("Continue", () -> triggerFadeOut(0.02)), getWidth() - 38, getHeight() - 16);

        triggerFadeIn(0.01);

        render();

        // TODO: add music
        Music.stop();
    }

    @Override
    public void act() {
        render();
        Timer.incrementAct();

        if (isFadeOutComplete()) {
            Greenfoot.setWorld(new SummaryWorld(simulationWorld));
        }
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
