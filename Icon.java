import greenfoot.*;

/**
 * An icon to distinguish UI Bars of each fisher.
 *
 * @author Matthew Li
 * @version April 2024
 */
public class Icon extends PixelActor
{
    private GreenfootImage boat = new GreenfootImage("boat_icon.png");
    private GreenfootImage hook = new GreenfootImage("hook_icon.png");
    private int type;

    /**
     * Create a specified type of icon.
     *
     * @param type 1 for boat icon, 2 for hook icon
     */
    public Icon(int type){
        super(Layer.UI);
        this.type = type;

        if(type == 1){
            boat.scale(16, 10);
            setImage(boat);
        }
        if(type == 2){
            hook.scale(11, 11);
            setImage(hook);
        }
    }
}
