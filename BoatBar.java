import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BoatBar here.
 *
 * @author Matthew Li
 * @version April 2024
 */
public class BoatBar extends UIBar
{
    private int level = 1;
    private int maxLevel = 3;
    private int exp = 1;
    private int maxExp = 1600;
    private int worldWidth = 125;
    private int worldHeight = 85;
    private int pixelsPerLevelPoint = (int)worldWidth/maxExp;

    private int width;
    private int height;

    private int actCounter = 0;
    private boolean facingRight;
    private GreenfootImage bar;
    private static GreenfootImage wrap = new GreenfootImage ("images\\Cropped_UI_Graphics.png");
    private static GreenfootImage fill = new GreenfootImage("images\\Water.jpg");

    //if you are chaging the pictures of the wrap, update these numbers. they are the pixel widths of your image
    //origonal width being how many pixels wide is your entire wrap, and origionalOffset is how many pixels of
    //offset to push the bar filling
    private static int originalWidth = 151;
    private static int originalOffset = 8;
    private static double barOffsetScalingFactor = ((double) originalOffset / (double) originalWidth);

    public BoatBar(int barWidth, int barHeight){
        //call the pixel actors constructor first (via the super() call )
        //pixel actors constructor requires a greenfootimage for it's constructor, so we use our helper static method to generate  it, and then pass it ot the UI

        super(barWidth, barHeight);
        this.width = barWidth;
        this.height = barHeight;
        setCenterOfRotation(0, 0);
    }

    public static GreenfootImage generateUI(int width, int height, double percentageFilled){
        wrap.scale(width, height);

        GreenfootImage barBackground = new GreenfootImage(width, height);
        barBackground.drawImage(wrap, 0, 0);

        int borderWidth = 1;

        int barOffset = (int) (width * barOffsetScalingFactor);
        //calculate how wide the "actual bar" can be (if fully filled)
        int filledSectionWidth = width - barOffset - 2;
        //multiply by how much to fill
        int barWidth  = (int) (filledSectionWidth * percentageFilled);

        if (barWidth > 0){
            GreenfootImage filledSection = Util.croppedImage(fill, barWidth, height - borderWidth *2 );
            barBackground.drawImage(filledSection, barOffset , borderWidth);
        }

        //instead of drawing it directly, RETURN the image
        return barBackground;
    }

    public void gainExp(int amount){
        if(exp < maxExp){
            exp += amount;
        }
        if(exp == maxExp && level < maxLevel){
            level++;
            exp = 0;
        }
        double barPercentage = (double) exp / (double)maxExp;
        //instead of calling update use our static helper method to "generate the updatedUI" and pass it to PixelActors set
        setImage(generateUI(this.width, this.height, barPercentage));
        setCenterOfRotation(0, 0);
    }

    public int getLevel() {
        return level;
    }
}