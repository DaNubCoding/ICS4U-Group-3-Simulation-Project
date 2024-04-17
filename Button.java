import greenfoot.*;

/**
 * A button that displays text,
 */
public class Button extends PixelActor {
    private Text text;
    private Runnable method;
    private boolean mouseDownOnThis;
    private boolean hovering;
    private GreenfootImage idleImage;
    private GreenfootImage hoverImage;
    private GreenfootImage clickImage;

    public Button(String text, Runnable method) {
        super();
        this.text = new Text(text, Text.AnchorX.CENTER, Text.AnchorY.CENTER);
        this.method = method;

        idleImage = getIdleImage();
        hoverImage = getHoverImage();
        clickImage = getClickImage();
        setImage(idleImage);
    }

    public void act() {
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if (mouseInfo == null) return;

        boolean isMouseOver = mouseOver(mouseInfo);
        if (isMouseOver && !hovering) {
            setImage(hoverImage);
        } else if (!isMouseOver && hovering) {
            setImage(idleImage);
        }
        hovering = isMouseOver;

        if (mouseInfo.getButton() == 1) {
            if (Greenfoot.mousePressed(null) && hovering) { // mouse down
                mouseDownOnThis = true;
                setImage(clickImage);
            } else if (Greenfoot.mouseClicked(null)) { // mouse up
                if (hovering && mouseDownOnThis) {
                    method.run();
                }
                mouseDownOnThis = false;
                setImage(idleImage);
            }
        }
    }

    /**
     * Determine if the mouse is hovering over the button.
     *
     * @return True if mouse is hovering over the button, false otherwise
     */
    private boolean mouseOver(MouseInfo mouseInfo) {
        int mouseX = mouseInfo.getX() / PixelWorld.PIXEL_SCALE;
        int mouseY = mouseInfo.getY() / PixelWorld.PIXEL_SCALE;
        int w = getOriginalWidth(), h = getOriginalHeight();
        boolean checkX = mouseX > getX() - w / 2 && mouseX < getX() + w / 2;
        boolean checkY = mouseY > getY() - h / 2 && mouseY < getY() + h / 2;
        return checkX && checkY;
    }

    private GreenfootImage getIdleImage() {
        GreenfootImage textImage = text.getOriginalImage();
        // Create the button image a bit larger than the text image
        GreenfootImage image = new GreenfootImage((int) (textImage.getWidth() * 1.6), (int) (textImage.getHeight() * 1.6));

        // Fill background and draw border
        image.setColor(new Color(255, 255, 255, 100));
        image.fill();
        image.setColor(new Color(0, 0, 0));
        image.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);

        // Draw the text at the center
        image.drawImage(textImage, (int) (textImage.getWidth() * 0.3), (int) (textImage.getHeight() * 0.3));
        return image;
    }

    private GreenfootImage getHoverImage() {
        GreenfootImage textImage = text.getOriginalImage();
        // Create the button image a bit larger than the text image
        GreenfootImage image = new GreenfootImage((int) (textImage.getWidth() * 1.6 + 2), (int) (textImage.getHeight() * 1.6 + 2));

        // Fill background and draw border
        image.setColor(new Color(255, 255, 240, 100));
        image.fill();
        image.setColor(new Color(0, 0, 0));
        image.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);

        // Draw the text at the center
        image.drawImage(textImage, (int) (textImage.getWidth() * 0.3 + 1), (int) (textImage.getHeight() * 0.3) + 1);
        return image;
    }

    private GreenfootImage getClickImage() {
        GreenfootImage textImage = text.getOriginalImage();
        // Create the button image a bit larger than the text image
        GreenfootImage image = new GreenfootImage((int) (textImage.getWidth() * 1.6), (int) (textImage.getHeight() * 1.6));

        // Fill background and draw border
        image.setColor(new Color(255, 255, 255, 200));
        image.fill();
        image.setColor(new Color(0, 0, 0));
        image.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);

        // Draw the text at the center
        image.drawImage(textImage, (int) (textImage.getWidth() * 0.3), (int) (textImage.getHeight() * 0.3));
        return image;
    }
}
