import greenfoot.*;

/**
 * UI element to show under fish in the summary world.
 *
 * @author Sandra Huang
 * @version April 2024
 */
public class Star extends PixelActor
{
    private GreenfootImage image;
    private static final GreenfootImage STAR = new GreenfootImage("summary/star.png");

    /**
     * Creates a Star object with the given number of stars.
     *
     * @param number the number of stars to display
     */
    public Star(int number){
        super(Layer.UI);
        image = new GreenfootImage(number*(STAR.getWidth()+1)-1, STAR.getHeight());
        for(int i=0; i<number; i++){
            image.drawImage(STAR, i*(STAR.getWidth()+1), 0);
        }
        setImage(image);
    }
}
