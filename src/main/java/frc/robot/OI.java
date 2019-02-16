/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

import frc.robot.lib.DPADButton;
import frc.robot.lib.FightStick;
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
  private Xbox d_driverOI = new Xbox(1, "Driver OI");
  private FightStick o_operatorOI = new FightStick(0, "Operator OI");

  /* Driver OI */
  private Button d_btn_RB = new JoystickButton(d_driverOI, Xbox.BTN_RB);
  private Button d_btn_LB = new JoystickButton(d_driverOI, Xbox.BTN_LB);
  private Button d_btn_A = new JoystickButton(d_driverOI, Xbox.BTN_A);
  private Button d_btn_X = new JoystickButton(d_driverOI, Xbox.BTN_X);
  private Button d_btn_B = new JoystickButton(d_driverOI, Xbox.BTN_B);
  private Button d_btn_Y = new JoystickButton(d_driverOI, Xbox.BTN_Y);
  private Button d_btn_back = new JoystickButton(d_driverOI, Xbox.BTN_BACK);
  private Button d_btn_start = new JoystickButton(d_driverOI, Xbox.BTN_START);
  private Button d_dpad_up = new DPADButton(d_driverOI, DPADButton.POV.UP);
  private Button d_dpad_down = new DPADButton(d_driverOI, DPADButton.POV.DOWN);
  private Button d_dpad_right = new DPADButton(d_driverOI, DPADButton.POV.RIGHT);
  private Button d_dpad_left = new DPADButton(d_driverOI, DPADButton.POV.LEFT);

  /* Operator OI */
  /* top buttons from left to right */
  private Button o_btn_share = new JoystickButton(o_operatorOI, FightStick.BTN_SHARE);
  private Button o_btn_options = new JoystickButton(o_operatorOI, FightStick.BTN_OPTIONS);
  private Button o_btn_L3 = new JoystickButton(o_operatorOI, FightStick.BTN_L3);
  private Button o_btn_R3 = new JoystickButton(o_operatorOI, FightStick.BTN_R3);

  /* middle buttons from left to right */
  private Button o_btn_RB = new JoystickButton(o_operatorOI, FightStick.BTN_RB);
  private Button o_btn_X = new JoystickButton(o_operatorOI, FightStick.BTN_X);
  private Button o_btn_Y = new JoystickButton(o_operatorOI, FightStick.BTN_Y);
  private Button o_btn_LB = new JoystickButton(o_operatorOI, FightStick.BTN_LB);

  /* bottom buttons from left to right */
  private Trigger o_axis_LT = new JoystickButton(o_operatorOI, FightStick.AXIS_LT);
  private Button o_btn_A = new JoystickButton(o_operatorOI, FightStick.BTN_A);
  private Button o_btn_B = new JoystickButton(o_operatorOI, FightStick.BTN_B);
  private Trigger o_axis_RT = new JoystickButton(o_operatorOI, FightStick.AXIS_RT);

  /* dpad buttons */
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

    /* Map d_driverOI buttons/triggers to commands */
    /* TODO: Add quick-turn to this.d_btn_LB (left 90 degres) and this.d_btn_RB (right 90 degrees) */
    this.d_btn_Y.whenPressed(new SetHighGear());
    this.d_btn_A.whenPressed(new SetLowGear());
    this.d_btn_X.whenPressed(new SwitchDrivingDirection()); // left
    this.d_btn_B.whenPressed(new DriveToggleRampingUsed()); // right /* TODO: Toggle break/coast mode for motor contollers */

    /* Map o_operaterOI buttons/triggers to commands */
    /* Wrist-related Commands */
    this.o_axis_LT.whenActive(new WristSetSpeed(-Constants.kMaxWristSpeed/2));
    this.o_axis_LT.whenInactive(new WristSetSpeed(0));
    this.o_btn_LB.whenPressed(new WristSetSpeed(-Constants.kMaxWristSpeed/2));
    //this.o_btn_LB.whenPressed(new WristSetSpeed(Constants.kMaxWristSpeed));
    this.o_btn_LB.whenReleased(new WristSetSpeed(0));
    //this.o_axis_LT.whenActive(new PresetWristControl(Robot.m_wrist.previousPreset()));
    //this.o_btn_LB.whenPressed(new PresetWristControl(Robot.m_wrist.nextPreset()));
    //this.o_axis_LT.whenActive(new PresetWristControl(Robot.m_wrist.setDownPreset()));
    //this.o_btn_LB.whenPressed(new PresetWristControl(Robot.m_wrist.setUpPreset()));  
    //this.o_btn_share.whenPressed(new PresentWristControl(Robot.m_wrist.setCargoShipPreset()));
    //this.o_btn_L3.whenPressed(new ToggleWristControl());
    this.o_axis_RT.whenActive(new WristSetSpeed(-Constants.kMaxWristSpeed/2));
    this.o_axis_RT.whenInactive(new WristSetSpeed(0));
    this.o_btn_RB.whenPressed(new WristSetSpeed(Constants.kMaxWristSpeed));
    this.o_btn_RB.whenReleased(new WristSetSpeed(0));

    /* Intake-related Commands */
    this.o_btn_options.whenPressed(new ToggleSmartIntake());
    this.o_btn_A.whileHeld(new EjectCargo());
    this.o_btn_A.whenReleased(new StopRollers());
    this.o_btn_X.whileHeld(new IntakeCargo());
    this.o_btn_X.whenReleased(new StopRollers());

    /* Hatch-related Commands */
    this.o_btn_B.whenPressed(new LaunchHatch());
    this.o_btn_Y.whenPressed(new ToggleBeak());

    /* Elevator related commands */
    //this.o_axis_RT.whenActive(new PresetElevatorControl(Robot.m_elevator.previousPreset()));
    //this.o_btn_RB.whenPressed(new PresetElevatorControl(Robot.m_elevator.nextPreset()));
    //this.o_btn_R3.whenPressed(new PresetElevatorControl(Robot.m_elevator.resetPreset()));
  }

  /* Used by the DriveTrain subsystem for default command */
  public Xbox getDriverOI() {
    return this.d_driverOI;
  }

  public FightStick getOperatorOI() {
    return this.o_operatorOI;
  }
}
