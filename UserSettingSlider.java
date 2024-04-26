import greenfoot.*;
import java.util.function.Consumer;

/**
 * A slider for the settings world that supplies updated values to a setter method.
 *
 * @author Andrew Wang
 * @author Martin Baldwin
 * @version April 2024
 */
public class UserSettingSlider<T extends Number> extends Slider {
    private Consumer<T> setter;

    public UserSettingSlider(T minValue, T maxValue, T defaultValue, int length, Color color, Consumer<T> setter) {
        super(minValue, maxValue, defaultValue, length, color);
        this.setter = setter;
    }

    @Override
    public void act() {
        super.act();
        setter.accept((T) getValue());
    }
}
