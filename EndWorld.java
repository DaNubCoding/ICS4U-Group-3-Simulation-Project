import greenfoot.*;

/**
 * A world where the user is informed of how the simulation ended.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class EndWorld extends PixelWorld {
    private static final int TEXT_WIDTH = 100;
    private static final int TEXT_HEIGHT = 12 * 11 - 1;
    private static final int TEXT_X = 16;
    private static final int TEXT_Y = (160 - TEXT_HEIGHT) / 2;

    private boolean keyPressed = true;

    private final SimulationWorld simulationWorld;
    private final GreenfootImage background;

    /**
     * Creates a new end world to display a specific ending depending on the
     * given end state.
     *
     * @param simulationWorld the SimulationWorld object that led to this EndWorld
     * @param state an {@link EndState} value for the end state to display
     */
    public EndWorld(SimulationWorld simulationWorld, EndState state) {
        super(250, 160);
        this.simulationWorld = simulationWorld;

        addObject(new AnimatedText(state.description, Text.AnchorX.LEFT, Text.AnchorY.TOP, TEXT_WIDTH, AnimatedText.Voice.BEEP), TEXT_X, TEXT_Y);
        addObject(new Button("Continue", () -> triggerFadeOut(0.02)), getWidth() - 38, getHeight() - 16);

        triggerFadeIn(0.01);

        background = state.background;
        getCanvas().setColor(state.color);
        render();

        Music.stop();
    }

    @Override
    public void act() {
        render();
        Timer.incrementAct();

        boolean newKeyPressed = Greenfoot.isKeyDown("enter");
        if (newKeyPressed && !keyPressed) {
            triggerFadeOut(0.02);
        }
        keyPressed = newKeyPressed;

        if (isFadeOutComplete()) {
            Greenfoot.setWorld(new SummaryWorld(simulationWorld));
        }
    }

    /**
     * Updates the display image of this world.
     */
    private void render() {
        GreenfootImage canvas = getCanvas();
        canvas.drawImage(background, 0, 0);
        canvas.fillRect(TEXT_X - Text.BACKGROUND_PADDING_X, TEXT_Y - Text.BACKGROUND_PADDING_Y, TEXT_WIDTH + Text.BACKGROUND_PADDING_X * 2, TEXT_HEIGHT + Text.BACKGROUND_PADDING_Y * 2);
        renderPixelActors();
        updateImage();
    }
}
