import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class that holds all fish settings for a basic, tier one fish
 *
 * @author Sandra
 * @version April 2024
 */
public abstract class TierOneFish extends Fish
{
    public TierOneFish(FishSettings settings, int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
    /**
     * Sets fish settings for tier one fish, according to defaults, while specifying the different body image, egg color, and evolutions
     *
     * @param settings the FishSettings to modify, unique to each subclass
     * @param bodyImage the specific image of the fish, unique to each subclass
     * @param eggColor the egg color, unique to each subclass
     * @param evolutions the possible evolutions, unique to each subclass
     */
    public static void initialiseTierOneFishSettings(FishSettings settings, String bodyImage, Egg.EggColor eggColor, Class<? extends Fish>... evolutions){
        // Base settings
        settings.setBaseValue(100);
        settings.setBodyImage(bodyImage);
        settings.setCatchOffset(13, 5);
        settings.setAllowedFeatures(FishFeature.HAT_PARTY, FishFeature.HAT_BROWN, FishFeature.ANGLER_SOCK, FishFeature.ANGLER_BOMB);
        // Movement settings
        settings.setSwimSpeed(0.2);
        settings.setMinDepth(10);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        settings.setAverageTurnInterval(200);
        settings.setMaxTurnDegrees(15);
        // Evolution settings
        settings.setEggSize(Egg.EggSize.SMALL);
        settings.setEggColor(eggColor);
        settings.setEggSpawnFrequency(2000);
        settings.setEvoPointGain(30);
        settings.setEvolutionChance(0.8);
        settings.setEvolutions(evolutions);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 3, -5);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 3, -2);
        settings.setFeaturePoint(FishFeature.ANGLER_SOCK, 9, -5);
        settings.setFeaturePoint(FishFeature.ANGLER_BOMB, 9, -5);
        // Sanity check to ensure all settings have been defined
        settings.validate();
    }
}
