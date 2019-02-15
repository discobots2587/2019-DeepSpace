package frc.robot.lib;

public class Xbox extends Gamepad {

	public static final int AXIS_LX = 0;
	public static final int AXIS_LY = 1;
	public static final int AXIS_LT = 2;
	public static final int AXIS_RT = 3;
	public static final int AXIS_RX = 4;
	public static final int AXIS_RY = 5;

	public static final int BTN_A = 1;
	public static final int BTN_B = 2;
	public static final int BTN_X = 3;
	public static final int BTN_Y = 4;
	public static final int BTN_LB = 5;
	public static final int BTN_RB = 6;

	public static final int BTN_BACK = 7;
	public static final int BTN_START = 8;

	public Xbox() {
		super(DEFAULT_USB_PORT);
	}

	public Xbox(int port) {
		super(port);
	}

	public Xbox(int port, String name) {
		super(port, name);
	}

	public double getLT() {
		return getRawAxis(AXIS_LT);
	}

	public double getRT() {
		return getRawAxis(AXIS_RT);
	}

}
