import greenfoot.*;

/**
 * A type of Text that can be hovered and produces a tooltip.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class TextWithTooltip extends Text {
    private Timer hoverTimer;
    private boolean hovering;
    private Text tooltip;

    /**
     * Creates a displayable text object from the given string with the
     * specified alignment and desired maximum render width.
     *
     * @param content the string to render to this text object
     * @param anchorX a {@link AnchorX} value describing horizontal alignment
     * @param anchorY a {@link AnchorY} value describing vertical alignment
     * @param maxWidth the desired maximum width of the rendered content
     * @see #reflowToWidth
     */
    public TextWithTooltip(String content, AnchorX anchorX, AnchorY anchorY) {
        super(content, anchorX, anchorY);
    }

    /**
     * Creates a displayable text object from the given integer with the
     * specified alignment.
     * <p>
     * This is identical to passing {@link String#valueOf(value)} as the content to {@link #Text(String, AnchorX, AnchorY)}.
     *
     * @param value the integer to render to this text object, using a base 10 representation
     * @param anchorX a {@link AnchorX} value describing horizontal alignment
     * @param anchorY a {@link AnchorY} value describing vertical alignment
     */
    public TextWithTooltip(String content, AnchorX anchorX, AnchorY anchorY, int maxWidth) {
        super(content, anchorX, anchorY, maxWidth);
    }

    /**
     * Updates this text object's image to display the given string.
     *
     * @param content the string to render to this text object
     */
    public TextWithTooltip(int value, AnchorX anchorX, AnchorY anchorY) {
        super(value, anchorX, anchorY);
    }

    /**
     * Set the content of the tooltip of this Text.
     *
     * @param content The content of the tooltip
     */
    public void setTooltipString(String content) {
        tooltip = new Text(content, Text.AnchorX.LEFT, Text.AnchorY.TOP, 100, new Color(248, 248, 248));
    }

    @Override
    public void act() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if (mouseInfo == null) return;

        boolean isMouseOver = mouseOver(mouseInfo);
        if (!hovering && isMouseOver) {
            hoverTimer = new Timer(40);
            hovering = true;
        }
        if (!isMouseOver) {
            hovering = false;
        }

        int mouseX = mouseInfo.getX() / PixelWorld.PIXEL_SCALE;
        int mouseY = mouseInfo.getY() / PixelWorld.PIXEL_SCALE;
        if (hoverTimer != null && hoverTimer.ended()) {
            if (tooltip.getWorld() == null) {
                getWorld().addObject(tooltip, 0, 0);
            }
        }
        if (tooltip.getWorld() != null) {
            tooltip.setLocation(mouseX + 3, mouseY + 3);

            // Remove tooltip if no longer hovering
            if (!hovering) {
                getWorld().removeObject(tooltip);
                hoverTimer = null;
            }
        }
    }

    /**
     * Determine if the mouse is hovering over the Text.
     *
     * @param mouseInfo The MouseInfo object obtained from Greenfoot
     * @return True if mouse is hovering over the Text, false otherwise
     */
    private boolean mouseOver(MouseInfo mouseInfo) {
        int mouseX = mouseInfo.getX() / PixelWorld.PIXEL_SCALE;
        int mouseY = mouseInfo.getY() / PixelWorld.PIXEL_SCALE;
        int w = getOriginalWidth(), h = getOriginalHeight();
        double factorX = getAnchorX().ordinal() * 0.5;
        double factorY = getAnchorY().ordinal() * 0.5;
        boolean checkX = mouseX > getX() - w * factorX && mouseX < getX() + w * (1 - factorX);
        boolean checkY = mouseY > getY() - h * factorY && mouseY < getY() + h * (1 - factorY);
        return checkX && checkY;
    }
}
