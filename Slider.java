import greenfoot.*;

/**
 * A slider used in the settings world.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class Slider extends PixelActor {
    /**
     * The draggable thumb on the slider.
     *
     * @author Andrew Wang
     * @version April 2024
     */
    private static class Thumb extends PixelActor {
        private int leftBound;
        private int rightBound;
        private Color color;
        private GreenfootImage normalImage;
        private GreenfootImage hoverImage;
        private boolean hovered;
        private boolean held;
        private int mouseOffsetX;

        /**
         * Create the thumb restricted to the left and right bounds of the
         * slider track.
         *
         * @param leftBound The minimum x-coordinate the Thumb can be moved to
         * @param rightBound The maximum x-coordinate the Thumb can be moved to
         * @param color The color of the Thumb
         */
        public Thumb(int leftBound, int rightBound, Color color) {
            super(createThumbImage(color));
            this.leftBound = leftBound;
            this.rightBound = rightBound;
            this.color = color;
            this.normalImage = getOriginalImage();
            this.hoverImage = createThumbImage(color.darker());
            held = false;
        }

        @Override
        public void act() {
            MouseInfo mouseInfo = Greenfoot.getMouseInfo();
            if (mouseInfo == null) return;

            // Scale the mouse position to the pixel scale of the world
            int mouseX = mouseInfo.getX() / PixelWorld.PIXEL_SCALE;
            int mouseY = mouseInfo.getY() / PixelWorld.PIXEL_SCALE;
            hovered = hovered(mouseX, mouseY);
            setImage(hovered ? hoverImage : normalImage);

            // Left mouse button pressed
            if (Greenfoot.mousePressed(null) && mouseInfo.getButton() == 1) {
                // Whether mouse is on top of the thumb
                if (hovered) {
                    held = true;
                    mouseOffsetX = mouseX - getX();
                }
            } else if (Greenfoot.mouseClicked(null)) {
                held = false;
            }

            if (held) {
                // Maintain the offset from where the mouse was pressed
                setX(mouseX - mouseOffsetX);
                // Restrain on the slider track
                if (getX() < leftBound) {
                    setX(leftBound);
                } else if (getX() > rightBound) {
                    setX(rightBound);
                }
            }
        }

        /**
         * Generate the image of the Thumb.
         *
         * @param color The color of the Thumb
         * @return The image of the Thumb
         */
        private static GreenfootImage createThumbImage(Color color) {
            GreenfootImage thumbImage = new GreenfootImage(4, 4);
            thumbImage.setColor(color);
            thumbImage.fill();
            return thumbImage;
        }

        /**
         * Set the new x-coordinate of the Thumb.
         * <p>Used as convenience method in Slider.</p>
         *
         * @param x The new x-coordinate
         */
        public void setX(int x) {
            setLocation(x, getY());
        }

        /**
         * Get whether the mouse is on top of this Thumb.
         *
         * @param mouseX The x-coordinate of the mouse
         * @param mouseY The y-coordinate of the mouse
         * @return True if being hovered, false otherwise
         */
        private boolean hovered(int mouseX, int mouseY) {
            // Get the bounds of the Thumb
            int left = getX() - getOriginalWidth() / 2;
            int right = getX() + getOriginalWidth() / 2;
            int top = getY() - getOriginalHeight() / 2;
            int bottom = getY() + getOriginalHeight() / 2;
            return mouseX >= left && mouseX <= right && mouseY >= top && mouseY <= bottom;
        }

        /**
         * Specifically for outside use by the Slider, there is only need to
         * calculate hover once in hovered().
         *
         * @return True is being hovered, false otherwise
         */
        public boolean isHovered() {
            return hovered;
        }
    }

    private int minValue;
    private int maxValue;
    private int length;
    private Color color;
    private Thumb thumb;
    private Text currentValueText;

    /**
     * Create a slider with integer max and min values of a certain length.
     *
     * @param minValue The minimum value of the Slider (left-most value)
     * @param maxValue The maximum value of the Slider (right-most value)
     * @param length The horizontal length of the slider
     * @param color The color of the slider
     */
    public Slider(int minValue, int maxValue, int length, Color color) {
        super(createTrackImage(length, color));

        this.minValue = minValue;
        this.maxValue = maxValue;
        this.length = length;
        this.color = color;

        setCenterOfRotation(0, 1);
    }

    @Override
    public void addedToWorld(World world) {
        thumb = new Thumb(getX(), getX() + length, color.brighter());
        // Place Thumb in the middle of the track
        world.addObject(thumb, getX() + length / 2, getY());

        currentValueText = new Text(0, Text.AnchorX.CENTER, Text.AnchorY.TOP);
        world.addObject(currentValueText, 0, 0);
        updateText();
    }

    @Override
    public void act() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if (mouseInfo == null) return;

        int mouseX = mouseInfo.getX() / PixelWorld.PIXEL_SCALE;
        int mouseY = mouseInfo.getY() / PixelWorld.PIXEL_SCALE;
        // Left mouse button pressed
        if (Greenfoot.mousePressed(null) && mouseInfo.getButton() == 1) {
            // If the thumb is hovered, handle drag with Thumb instead
            if (hovered(mouseX, mouseY) && !thumb.isHovered()) {
                thumb.setX(mouseX);
            }
        }

        updateText();
    }

    /**
     * Generate the image of the track based on the length and color.
     *
     * @param length The length of the track
     * @param color The color of the track
     * @return The image of the track
     */
    private static GreenfootImage createTrackImage(int length, Color color) {
        GreenfootImage trackImage = new GreenfootImage(length, 2);
        trackImage.setColor(color);
        trackImage.fill();
        return trackImage;
    }

    /**
     * Get whether the mouse is on top of the Slider.
     *
     * @param mouseX The x-coordinate of the mouse
     * @param mouseY The y-coordinate of the mouse
     * @return True if being hovered, false otherwise
     */
    private boolean hovered(int mouseX, int mouseY) {
        // Get the bounds of the Slider
        int left = getX();
        int right = getX() + length;
        // Highest and lowest y to still count as clicking on the slider
        int top = getY() - thumb.getOriginalHeight() / 2;
        int bottom = getY() + thumb.getOriginalHeight() / 2;
        return mouseX > left && mouseX < right && mouseY > top && mouseY < bottom;
    }

    /**
     * Get the current value of the slider.
     *
     * @return The current value
     */
    public int getValue() {
        return (maxValue - minValue) * (thumb.getX() - getX()) / length + minValue;
    }

    /**
     * Update the location and content of the text.
     */
    private void updateText() {
        currentValueText.setLocation(thumb.getX(), thumb.getY() + thumb.getOriginalHeight() / 2 + 2);
        currentValueText.setContent(getValue());
    }
}
