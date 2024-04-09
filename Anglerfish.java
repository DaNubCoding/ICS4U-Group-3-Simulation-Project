import greenfoot.*;
import java.util.Map;
import java.util.EnumMap;
import java.util.Collections;

/**
 * A large deep sea fish.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class Anglerfish extends Fish {
    // Amount of XP this Fish type is worth without any features
    private static final int BASE_XP_VALUE = 100;

    // Base image of this Fish type without any features
    private static final GreenfootImage bodyImage = new GreenfootImage("anglerfish.png");
    // Point relative to body image where this Fish type may be caught from
    private static final IntPair CATCH_OFFSET = new IntPair(27, 18);

    // Image offsets for all types of features
    // Each IntPair defines the x and y offsets of the top left corner of each
    // feature's image from the top left corner of the body image
    private static final Map<FishFeature, IntPair> FEATURE_POINTS;
    static {
        Map<FishFeature, IntPair> m = new EnumMap<>(FishFeature.class);
        m.put(FishFeature.BIG_EYE, new IntPair(19, 8));
        FEATURE_POINTS = Collections.unmodifiableMap(m);
    }

    public Anglerfish(FishFeature... features) {
        super(features);
    }

    @Override
    public void act() {
        // TODO
        move(0.1);
        if (Util.randInt(0, 120) == 0) {
            if (hasFeature(FishFeature.BIG_EYE)) {
                removeFeature(FishFeature.BIG_EYE);
            } else {
                addFeature(FishFeature.BIG_EYE);
            }
        }
    }

    @Override
    public GreenfootImage getBodyImage() {
        return bodyImage;
    }

    @Override
    public IntPair getCatchOffset() {
        return CATCH_OFFSET;
    }

    @Override
    public Map<FishFeature, IntPair> getFeaturePoints() {
        return FEATURE_POINTS;
    }

    @Override
    public int getBaseValue() {
        return BASE_XP_VALUE;
    }
}
