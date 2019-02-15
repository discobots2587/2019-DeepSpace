
package frc.robot.lib;

import edu.wpi.first.wpilibj.Joystick;

public class Gamepad extends Joystick {

	//Use 'X' not 'D' on the back of the controller
	protected static final int DEFAULT_USB_PORT = 0;

	public String name = "Gamepad";

	public static int AXIS_LX = 0;
	public static int AXIS_LY = 1;
	public static int AXIS_LT = 2;
	public static int AXIS_RT = 3;
	public static int AXIS_RX = 4;
	public static int AXIS_RY = 5;

	public static int BTN_A = 1;
	public static int BTN_B = 2;
	public static int BTN_X = 3;
	public static int BTN_Y = 4;
	public static int BTN_LB = 5;
	public static int BTN_RB = 6;

	public static int BTN_BACK = 7;
	public static int BTN_START = 8;

	/**
	 * Creates a gamepad with name "Gamepad" in USB port 0
	 */
	public Gamepad() {
		super(DEFAULT_USB_PORT);
	}

	/**
	 * Creates a gamepad with name "Gamepad" in USB port <i>port</i>
	 * @param port USB port
	 */
	public Gamepad(int port) {
		super(port);
	}

	/**
	 * Creates a gamepad with name <i>name</i> in USB port <i>port</i>
	 * @param port USB port
	 * @param name Gamepad name
	 */
	public Gamepad(int port, String name) {
		super(port);
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public double getLX() {
		double val = this.getRawAxis(AXIS_LX);
		return val;
	}

	public double getLY() {
		double val = this.getRawAxis(AXIS_LY) * -1;
		return val;
	}

	public double getRX() {
		double val = this.getRawAxis(AXIS_RX);
		return val;
	}

	public double getRY() {
		double val = this.getRawAxis(AXIS_RY) * -1;
		return val;
	}

	public double getDPAD() {
		return getDPADfromPOV(this.getPOV());
	}

	public static double getDPADfromPOV(double pov) {
		if (45 <= pov && pov < 135) {
			pov = 90;
		} else if (135 <= pov && pov < 225) {
			pov = 180;
		} else if (225 <= pov && pov < 315) {
			pov = 270;
		} else {
			pov = 0;
		}
		return pov;
	}

}
