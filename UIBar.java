import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ExpBar here.
 *
 * @author Matthew Li
 * @version April 2024
 */
public abstract class UIBar extends PixelActor
{
    private int level = 1;
    private int maxLevel = 3;
    private int exp = 1;
    private int maxExp = 2000;
    private int worldWidth = 125;
    private int worldHeight = 65;
    private int pixelsPerLevelPoint = (int)worldWidth/maxExp;

    private int width;
    private int height;

    private int actCounter = 0;
    private GreenfootImage bar;
    private static GreenfootImage wrap = new GreenfootImage ("images\\Cropped_UI_Graphics.png");
    private static GreenfootImage fill = new GreenfootImage("images\\Water.jpg");

    //if you are chaging the pictures of the wrap, update these numbers. they are the pixel widths of your image
    //origonal width being how many pixels wide is your entire wrap, and origionalOffset is how many pixels of
    //offset to push the bar filling
    private static int originalWidth = 151;
    private static int originalOffset = 25;
    private static double barOffsetScalingFactor = ((double) UIBar.originalOffset / (double) UIBar.originalWidth);

    //given all the parameters generate the new sprite image
    //this is based on your update() method but with all the varialbes passed in via parameters as this is a static method
    //i put in default values becuase I don't think java lets you use non-static variables in static methods

    //what you SHOULD do on your own is define constant static "inital values" up top, and assign them to your instance variables in your constructor
    //for now I just hardcoded the initial values inside the prameters of this method

    public UIBar(int barWidth, int barHeight){
        //call teh pixel actors constructor first (via the super() call )
        //pixel actors constructor requires a greenfootimage for it's constructor, so we use our helper static method to generate  it, and then pass it ot the UI

        super(generateUI(barWidth ,barHeight , 0));

        this.width = barWidth;
        this.height = barHeight;
    }

    public static GreenfootImage generateUI(int width, int height, double percentageFilled){
        wrap.scale(width, height);

        GreenfootImage barBackground = new GreenfootImage(width, height);
        barBackground.drawImage(wrap, 0, 0);

        int borderWidth = 2;

        //barBackground.setColor(Color.RED);
        //barBackground.drawRect(200, 0, width, height - 2);
        //barBackground.fill();
        int barOffset = (int) (width * UIBar.barOffsetScalingFactor);
        //calculate how wide the "actual bar" can be (if fully filled)
        int filledSectionWidth = width - barOffset - 2;
        //multiply by how much to fill
        int barWidth  = (int) (filledSectionWidth * percentageFilled);

        if (barWidth > 0 ){
            GreenfootImage filledSection = Util.croppedImage(UIBar.fill, barWidth, height - borderWidth *2 );
            barBackground.drawImage(filledSection, barOffset , borderWidth);
        }

        //instead of drawing it directly, RETURN the image
        return barBackground;
    }

    // public void act()
    // {
    //     // Add your action code here.
    //     actCounter++;

    //     if (actCounter % 55 == 0 ){
    //         gainExp(1);
    //         double barPercentage = (double ) exp / (double)maxExp;

    //         //instead of calling update use our static helper method to "generate the updatedUI" and pass it to PixelActors set
    //         //update();
    //         super.setImage(generateUI(this.width, this.height, barPercentage));
    //     }
    // }

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
        //update();
        super.setImage(generateUI(this.width, this.height, barPercentage));
    }
}
