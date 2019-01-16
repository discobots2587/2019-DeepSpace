package frc.robot.lib;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class DPADButton extends Button {

	private Joystick js;
	private POV pov;
	private boolean locked;

	public enum POV {
		UP(0), DOWN(180), RIGHT(90), LEFT(270), UP_RIGHT(45), DOWN_RIGHT(135), DOWN_LEFT(225), UP_LEFT(315);

		private final double val;

		private POV(double val) {
			this.val = val;
		}
	}

	/**
	 * DPADButton from POV direction <i>pov</i> of Joystick <i>js</i>
	 * <p>
	 * DPADButton can only be Up, Down, Left, Right
	 * @param js Joystick
	 * @param pov pov enum
	 */
	public DPADButton(Joystick js, POV pov) {
		this(js,pov,true);
	}

	/**
	 * DPADButton from POV direction <i>pov</i> of Joystick <i>js</i>
	 * <p>
	 * If locked is true, DPADButton can only be Up, Down, Left, Right
	 * <p>
	 * If locked is false, DPADButton can only be Up, Down, Left, Right, Up_Right, Up_Left, Down_Right, Down_Left
	 * @param js Joystick
	 * @param pov pov enum
	 * @param locked Locked boolean (only Up, Down, Left, Right)
	 */
	public DPADButton(Joystick js, POV pov, boolean locked) {
		this.js = js;
		this.pov = pov;
	}

	@Override
	public boolean get() {
		double val = this.pov.val;
		double currentVal = js.getPOV();
		if (this.locked)
			currentVal = Gamepad.getDPADfromPOV(currentVal);
		if (currentVal == val) {
			return true;
		}
		return false;
	}

}
