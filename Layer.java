/**
 * Separate layers which PixelActor objects may belong to, defining the
 * order in which they are rendered.
 * <p>
 * Actors belonging to later-defined layers in this enum are drawn on top of
 * earlier-defined layers.
 *
 * @author Martin Baldwin
 * @version April 2024
 */
public enum Layer {
    BACKGROUND,
    BG_BUBBLE, // background bubbles
    FISHING_ROD, BOAT,
    FISH,
    FG_BUBBLE, // foreground bubbles
    FISHING_LINE, HOOK,
    EFFECT,
    FOREGROUND,
    UI;
}
