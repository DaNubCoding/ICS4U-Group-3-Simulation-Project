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
    EGG,
    BUBBLE,
    FISHING_ROD, BOAT,
    FISH,
    FISHING_LINE, HOOK,
    EFFECT,
    FOREGROUND,
    UI;
}
