import greenfoot.*;

public class Test extends PixelActor {
	public Test() {
		super("test.png");
		setHeading(45);
	}

	public void act() {
		setRotation(getRotation() + 1);
		setHeading(getHeading() + 0.5);
		move(0.2);
	}
}
