/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.lib.AXISButton;
import frc.robot.lib.DPADButton;
import frc.robot.lib.Gamepad;
import frc.robot.lib.LogitechController;
import frc.robot.lib.Xbox;

import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  /*
   * CREATING BUTTONS
   * One type of button is a joystick button which is any button on a joystick.
   * You create one by telling it which joystick it's on and which button number
   * it is.
   *   Joystick stick = new Joystick(port);
   *   Button button = new JoystickButton(stick, buttonNumber);
   *
   * There are a few additional built-in buttons you can use. Additionally,
   * by subclassing Button, you can create custom triggers and bind those to
   * commands the same as any other Button.
   *
   * TRIGGERING COMMANDS WITH BUTTONS
   * Once you have a button, it's trivial to bind it to a button in one of
   * three ways:
   *
   * 1) Start the command when the button is pressed and let it run the command
   *    until it is finished as determined by it's isFinished method.
   *      button.whenPressed(new ExampleCommand());
   *
   * 2) Run the command while the button is being held down and interrupt it
   *    once the button is released.
   *      button.whileHeld(new ExampleCommand());
   *
   * 3) Start the command when the button is released and let it run the command
   *    until it is finished as determined by it's isFinished method.
   *       button.whenReleased(new ExampleCommand());
   */

  public static enum controller {
    XBOX,         // 0
    DUALSHOCK     // 1
  }

  /*
   * OI-related variable naming conventions:
   *    - driverOI-related variables: "d_" followed by the member variable
   *        starting with a lower case letter then camel case
   *    - operatorOI-related variables: "o_" followed by the member variable
   *        starting with a lower case letter then camel case
   */
  private LogitechController d_driverOI = new LogitechController(0, "Driver OI");
  /* TODO: Instantiate o_operatorOI object */

  /* Driver OI */
  private Button d_btn_RB = new JoystickButton(d_driverOI, LogitechController.BTN_RB);
  private Button d_btn_LB = new JoystickButton(d_driverOI, LogitechController.BTN_LB);
  private Button d_btn_back = new JoystickButton(d_driverOI, LogitechController.BTN_BACK);
  private Button d_btn_start = new JoystickButton(d_driverOI, LogitechController.BTN_START);
  private Button d_btn_A = new JoystickButton(d_driverOI, LogitechController.BTN_A);
  private Button d_btn_X = new JoystickButton(d_driverOI, LogitechController.BTN_X);
  private Button d_btn_B = new JoystickButton(d_driverOI, LogitechController.BTN_B);
  private Button d_btn_Y = new JoystickButton(d_driverOI, LogitechController.BTN_Y);
  private Button d_dpad_up = new DPADButton(d_driverOI, DPADButton.POV.UP);
  private Button d_dpad_down = new DPADButton(d_driverOI, DPADButton.POV.DOWN);
  private Button d_dpad_right = new DPADButton(d_driverOI, DPADButton.POV.RIGHT);
  private Button d_dpad_left = new DPADButton(d_driverOI, DPADButton.POV.LEFT);

  /* Operator OI */
  /* TODO: Setup o_operatorOI-related buttons */

  public OI() {
    /* TODO: Determine if Dashboard.controllerChooser is needed */
    /*
    switch(Dashboard.controllerChooser.getSelected()) {
      case XBOX:
        this.d_driverOI = new Xbox(0, "Driver OI");
        this.d_btn_RT = new AXISButton(d_driverOI, Gamepad.BTN_RT);
        this.d_btn_LT = new AXISButton(d_driverOI, Gamepad.BTN_LT);
        break;
      case DUALSHOCK:
        break;
      default:
        break;
    }
    */

    /* TODO: Add d_driverOI button commands (if any) */
    this.d_btn_A.whenPressed(new DriveToggleRampingUsed());

      d_btn_LB.whenPressed(new LaunchHatch());
      d_btn_A.whenActive(new BeakSet());
      d_btn_RB.whenPressed(new ToggleSmartIntake());
      //d_btn_RT.whileHeld(new EjectCargo());
      //d_btn_LT.whileHeld(new IntakeCargo());
      
    /* TODO: Add o_operaterOI button commands */
  }

  /* Used by the DriveTrain subsystem for default command */
  public LogitechController getDriverOI() {
    return this.d_driverOI;
  }
}
