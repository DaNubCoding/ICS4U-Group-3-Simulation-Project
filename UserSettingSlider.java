import greenfoot.*;

public class UserSettingSlider<T extends Number> extends Slider {
    public static interface Setter<T extends Number> {
        public void run(T value);
    }

    private Setter<T> setter;

    public UserSettingSlider(T minValue, T maxValue, T defaultValue, int length, Color color, Setter<T> setter) {
        super(minValue, maxValue, defaultValue, length, color);
        this.setter = setter;
    }

    public void act() {
        super.act();
        setter.run((T) getValue());
    }
}
