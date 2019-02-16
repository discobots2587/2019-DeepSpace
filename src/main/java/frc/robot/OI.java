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
import frc.robot.util.Constants;

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
  private LogitechController o_operatorOI = new LogitechController(1, "Operator OI");

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
  private Button o_btn_RB = new JoystickButton(o_operatorOI, LogitechController.BTN_RB);
  private Button o_btn_LB = new JoystickButton(o_operatorOI, LogitechController.BTN_LB);
  private Button o_btn_back = new JoystickButton(o_operatorOI, LogitechController.BTN_BACK);
  private Button o_btn_start = new JoystickButton(o_operatorOI, LogitechController.BTN_START);
  private Button o_btn_A = new JoystickButton(o_operatorOI, LogitechController.BTN_A);
  private Button o_btn_X = new JoystickButton(o_operatorOI, LogitechController.BTN_X);
  private Button o_btn_B = new JoystickButton(o_operatorOI, LogitechController.BTN_B);
  private Button o_btn_Y = new JoystickButton(o_operatorOI, LogitechController.BTN_Y);
  private Button o_dpad_up = new DPADButton(o_operatorOI, DPADButton.POV.UP);
  private Button o_dpad_down = new DPADButton(o_operatorOI, DPADButton.POV.DOWN);
  private Button o_dpad_right = new DPADButton(o_operatorOI, DPADButton.POV.RIGHT);
  private Button o_dpad_left = new DPADButton(o_operatorOI, DPADButton.POV.LEFT);

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
    //this.d_btn_A.whenPressed(new DriveToggleRampingUsed());

    this.d_btn_LB.whenPressed(new LaunchHatch());
    this.d_btn_A.whenPressed(new ToggleBeak());

    this.d_btn_X.whenPressed(new SwitchCamera());

    this.d_btn_RB.whenPressed(new ToggleSmartIntake());
    this.d_btn_Y.whileHeld(new EjectCargo());
    this.d_btn_Y.whenReleased(new StopRollers());
    this.d_btn_X.whileHeld(new IntakeCargo());
    this.d_btn_X.whenReleased(new StopRollers());

    /* TODO: Add o_operaterOI button commands */
    //this.o_btn_RB.whenPressed(new PresetWristControl(Robot.m_wrist.nextPreset()));
    //this.o_btn_LB.whenPressed(new PresetWristControl(Robot.m_wrist.previousPreset()));

    //this.o_btn_A.whenPressed(new ToggleWristControl());

    this.o_btn_Y.whenPressed(new WristSetSpeed(Constants.kMaxWristSpeed));
    this.o_btn_Y.whenReleased(new WristSetSpeed(0));
    this.o_btn_A.whenPressed(new WristSetSpeed(-Constants.kMaxWristSpeed/2));
    this.o_btn_A.whenReleased(new WristSetSpeed(0));
  }

  /* Used by the DriveTrain subsystem for default command */
  public LogitechController getDriverOI() {
    return this.d_driverOI;
  }

  public LogitechController getOperatorOI() {
    return this.o_operatorOI;
  }
}
