import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ExpBar here.
 *
 * @author Matthew Li
 * @version April 2024
 */
public class UIBar extends PixelActor
{
    private static final SoundEffect upgradeSound = new SoundEffect("upgrade.wav", 4);

    private int level = 1;
    private int maxLevel = 3;
    private int exp = 1;
    private int maxExp;

    private int width;
    private int height;

    private static GreenfootImage wrap = new GreenfootImage ("ui/bar_background.png");
    private GreenfootImage fill;

    //if you are chaging the pictures of the wrap, update these numbers. they are the pixel widths of your image
    //borderWidth is how many pixels of offset to push the bar filling
    private static int borderWidth = 1;

    //given all the parameters generate the new sprite image
    //this is based on your update() method but with all the varialbes passed in via parameters as this is a static method
    //i put in default values becuase I don't think java lets you use non-static variables in static methods

    //what you SHOULD do on your own is define constant static "inital values" up top, and assign them to your instance variables in your constructor
    //for now I just hardcoded the initial values inside the prameters of this method

    public UIBar(int barWidth, int barHeight, int maxExp, String fillImagePath){
        //call teh pixel actors constructor first (via the super() call )
        //pixel actors constructor requires a greenfootimage for it's constructor, so we use our helper static method to generate  it, and then pass it ot the UI
        super(generateUI(barWidth ,barHeight , 0, new GreenfootImage(fillImagePath)), Layer.UI);

        this.width = barWidth;
        this.height = barHeight;
        this.maxExp = maxExp;
        this.fill = new GreenfootImage(fillImagePath);
        setCenterOfRotation(0, 0);
    }

    public static GreenfootImage generateUI(int width, int height, double percentageFilled, GreenfootImage fill){
        wrap.scale(width, height);

        GreenfootImage barBackground = new GreenfootImage(width, height);
        barBackground.drawImage(wrap, 0, 0);

        //calculate how wide the "actual bar" can be (if fully filled)
        int filledSectionWidth = width - borderWidth * 2;
        //multiply by how much to fill
        int barWidth  = (int) (filledSectionWidth * percentageFilled);

        if (barWidth > 0 ){
            GreenfootImage filledSection = Util.croppedImage(fill, barWidth, height - borderWidth *2 );
            barBackground.drawImage(filledSection, borderWidth , borderWidth);
        }

        //instead of drawing it directly, RETURN the image
        return barBackground;
    }

    public void gainExp(int amount){
        exp += amount;
        if(exp >= maxExp){
            if(level < maxLevel){
                level++;
                upgradeSound.play();
                exp = 0;
            }else{
                exp = maxExp;
            }
        }

        double barPercentage = (double) exp / (double)maxExp;
        //instead of calling update use our static helper method to "generate the updatedUI" and pass it to PixelActors set
        setImage(generateUI(this.width, this.height, barPercentage, fill));
        setCenterOfRotation(0, 0);
    }

    /**
     * Get the current level of the UIBar.
     *
     * @return The current level of the UIBar
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get the current exp of the UIBar.
     *
     * @return The current exp of the UIBar
     */
    public int getExp() {
        return exp;
    }

    /**
     * Get the maximum exp of the UIBar.
     *
     * @return The maximum exp of the UIBar
     */
    public int getMaxExp() {
        return maxExp;
    }
}
