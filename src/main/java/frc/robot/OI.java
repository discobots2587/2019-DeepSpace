/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import frc.robot.lib.Gamepad;
import frc.robot.lib.DPADButton;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

//importing all the commands


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  public Gamepad p_gp = new Gamepad(0, "Primary");
  //create all the buttons for primary controller
	private Button p_btn_RB = new JoystickButton(p_gp, Gamepad.BTN_RB);
	private Button p_btn_LB = new JoystickButton(p_gp, Gamepad.BTN_LB);
	private Button p_btn_RT = new JoystickButton(p_gp, Gamepad.BTN_RT);
	private Button p_btn_LT = new JoystickButton(p_gp, Gamepad.BTN_LT);
	private Button p_btn_back = new JoystickButton(p_gp, Gamepad.BTN_BACK);
	private Button p_btn_start = new JoystickButton(p_gp, Gamepad.BTN_START);
	private Button p_btn_A = new JoystickButton(p_gp, Gamepad.BTN_A);
	private Button p_btn_X = new JoystickButton(p_gp, Gamepad.BTN_X);
	private Button p_btn_B = new JoystickButton(p_gp, Gamepad.BTN_B);
  private Button p_btn_Y = new JoystickButton(p_gp, Gamepad.BTN_Y);
  /*
	private Button p_dpad_up = new DPADButton(p_gp, POV.UP);
	private Button p_dpad_down = new DPADButton(p_gp, POV.DOWN);
	private Button p_dpad_right = new DPADButton(p_gp, POV.RIGHT);
  private Button p_dpad_left = new DPADButton(p_gp, POV.LEFT);
  */
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
