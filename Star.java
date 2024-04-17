import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * UI element to show under fish in the summary world
 *
 * @author Sandra Huang
 * @version (a version number or a date)
 */
public class Star extends PixelActor
{
    private GreenfootImage image;
    private static final GreenfootImage STAR = new GreenfootImage("summary/star.png");

    public Star(int number){
        image = new GreenfootImage(number*(STAR.getWidth()+1)-1, STAR.getHeight());
        for(int i=0; i<number; i++){
            image.drawImage(STAR, i*(STAR.getWidth()+1), 0);
        }
        setImage(image);
    }
}
