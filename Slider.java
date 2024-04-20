import greenfoot.*;

/**
 * A slider used in the settings world.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class Slider<T extends Number> extends PixelActor {
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
            super(createThumbImage(color), Layer.UI);
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
         * @return True if being hovered, false otherwise
         */
        public boolean isHovered() {
            return hovered;
        }

        /**
         * Whether the Thumb is being dragged or not.
         *
         * @return
         */
        public boolean isHeld() {
            return held;
        }
    }

    private T minValue;
    private T maxValue;
    private T defaultValue;
    private int length;
    private Color color;
    private Thumb thumb;
    private Text currentValueText;

    /**
     * Create a slider with max and min values of a certain length.
     *
     * @param minValue The minimum value of the Slider (left-most value)
     * @param maxValue The maximum value of the Slider (right-most value)
     * @param defaultValue The default value the Slider will be initalized at
     * @param length The horizontal length of the slider
     * @param color The color of the slider
     */
    public Slider(T minValue, T maxValue, T defaultValue, int length, Color color) {
        super(createTrackImage(length, color), Layer.UI);

        this.minValue = minValue;
        this.maxValue = maxValue;
        this.defaultValue = defaultValue;
        this.length = length;
        this.color = color;

        setCenterOfRotation(0, 1);
    }

    @Override
    public void addedToWorld(World world) {
        thumb = new Thumb(getX(), getX() + length, color.brighter());
        // Place Thumb in the middle of the track
        double range = maxValue.doubleValue() - minValue.doubleValue();
        int thumbX = (int) (getX() + length * (defaultValue.doubleValue() - minValue.doubleValue()) / range);
        world.addObject(thumb, thumbX, getY());

        currentValueText = new Text(0, Text.AnchorX.CENTER, Text.AnchorY.TOP);
        world.addObject(currentValueText, 0, 0);
        updateText(false);
    }

    @Override
    public void act() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if (mouseInfo == null) return;

        int mouseX = mouseInfo.getX() / PixelWorld.PIXEL_SCALE;
        int mouseY = mouseInfo.getY() / PixelWorld.PIXEL_SCALE;
        boolean isHovered = hovered(mouseX, mouseY);
        // Left mouse button pressed
        if (Greenfoot.mousePressed(null) && mouseInfo.getButton() == 1) {
            // If the thumb is hovered, handle drag with Thumb instead
            if (isHovered && !thumb.isHovered()) {
                thumb.setX(mouseX);
            }
        }

        updateText(isHovered || thumb.isHovered());
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
     * Get the current value of the slider as a number.
     * <p>The value must be retrieved via methods like {@code .doubleValue()}
     * or {@code .intValue()}</p>
     *
     * @return The current value as a number
     */
    @SuppressWarnings("unchecked")
    public T getValue() {
        double range = maxValue.doubleValue() - minValue.doubleValue();
        int thumbOffsetX = thumb.getX() - getX();
        double value = range * thumbOffsetX / length + minValue.doubleValue();
        if (minValue instanceof Integer) {
            return (T) Integer.valueOf((int) value);
        } else if (minValue instanceof Double) {
            return (T) Double.valueOf((double) Math.round(value * 100) / 100);
        } else {
            throw new IllegalArgumentException("Unsupported number type");
        }
    }

    /**
     * Get the current value of the slider as a String.
     *
     * @return The current value of the slider as a String
     */
    public String getValueAsString() {
        if (minValue instanceof Double) {
            return String.format("%.2f", getValue());
        }
        return getValue().toString();
    }

    /**
     * Update the location and content of the text.
     *
     * @param isHovered True if the Slider is being hovered, false otherwise
     */
    private void updateText(boolean isHovered) {
        currentValueText.setLocation(thumb.getX(), thumb.getY() + thumb.getOriginalHeight() / 2 + 2);
        currentValueText.setContent(getValueAsString());

        // Make text more opaque on hover
        currentValueText.setTransparency(isHovered || thumb.isHeld() ? 255 : 128);
    }
}
