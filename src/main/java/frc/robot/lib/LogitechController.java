/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.lib;

import edu.wpi.first.wpilibj.Joystick;

public class LogitechController extends Joystick {

	//Use 'D' not 'X'
	protected static final int DEFAULT_USB_PORT = 0;

	public String name = "Logitech";

	public static int AXIS_LX = 0; // Left joystick X-axis
	public static int AXIS_LY = 1; // Left joystick Y-axis
    public static int AXIS_LT = 2; // Left trigger
	public static int AXIS_RT = 3; // Right trigger
	public static int AXIS_RX = 4; // Right joystick X-axis
    public static int AXIS_RY = 5; // Right joystick Y-axis

	public static int BTN_A = 1;
	public static int BTN_B = 2;
	public static int BTN_X = 3;
    public static int BTN_Y = 4;
	public static int BTN_LB = 5;
    public static int BTN_RB = 6;
	public static int BTN_BACK = 7;
	public static int BTN_START = 8;

	/**
	 * Creates a Logitech with name "Logitech" in USB port 0
	 */
	public LogitechController() {
		super(DEFAULT_USB_PORT);
	}

	/**
	 * Creates a Logitech with name "Logitech" in USB port <i>port</i>
	 * @param port USB port
	 */
	public LogitechController(int port) {
		super(port);
	}

	/**
	 * Creates a Logitech with name <i>name</i> in USB port <i>port</i>
	 * @param port USB port
	 * @param name Logitech name
	 */
	public LogitechController(int port, String name) {
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

