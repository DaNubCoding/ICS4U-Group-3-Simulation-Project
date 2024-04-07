import greenfoot.*;
import java.util.Map;
import java.util.EnumMap;

/**
 * A large deep sea fish.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public class Anglerfish extends Fish {
    private static final GreenfootImage bodyImage = new GreenfootImage("images/anglerfish.png");

    private static final Map<FishFeature, IntPair> FEATURE_POINTS;
    static {
        FEATURE_POINTS = new EnumMap(FishFeature.class);
        FEATURE_POINTS.put(FishFeature.BIG_EYE, new IntPair(19, 8));
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
    public Map<FishFeature, IntPair> getFeaturePoints() {
        return FEATURE_POINTS;
    }
}
