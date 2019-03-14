/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import frc.robot.lib.AXISButton;
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
  private Xbox o_operatorOI = new Xbox(0, "Operator OI");
  private boolean driverOperatorEnabled;

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
  private AXISButton d_axisBtn_LT = new AXISButton(o_operatorOI, Xbox.AXIS_LT, Constants.kAxisButtonSensitivity);
  private AXISButton d_axisBtn_RT = new AXISButton(o_operatorOI, Xbox.AXIS_RT, Constants.kAxisButtonSensitivity);

  /* Operator OI */
  private Button o_btn_RB = new JoystickButton(o_operatorOI, Xbox.BTN_RB);
  private Button o_btn_LB = new JoystickButton(o_operatorOI, Xbox.BTN_LB);
  private Button o_btn_A = new JoystickButton(o_operatorOI, Xbox.BTN_A);
  private Button o_btn_X = new JoystickButton(o_operatorOI, Xbox.BTN_X);
  private Button o_btn_B = new JoystickButton(o_operatorOI, Xbox.BTN_B);
  private Button o_btn_Y = new JoystickButton(o_operatorOI, Xbox.BTN_Y);
  private Button o_btn_back = new JoystickButton(o_operatorOI, Xbox.BTN_BACK);
  private Button o_btn_start = new JoystickButton(o_operatorOI, Xbox.BTN_START);
  private AXISButton o_axisBtn_LT = new AXISButton(o_operatorOI, Xbox.AXIS_LT, Constants.kAxisButtonSensitivity);
  private AXISButton o_axisBtn_RT = new AXISButton(o_operatorOI, Xbox.AXIS_RT, Constants.kAxisButtonSensitivity);
  private Button o_dpad_up = new DPADButton(o_operatorOI, DPADButton.POV.UP);
  private Button o_dpad_down = new DPADButton(o_operatorOI, DPADButton.POV.DOWN);
  private Button o_dpad_right = new DPADButton(o_operatorOI, DPADButton.POV.RIGHT);
  private Button o_dpad_left = new DPADButton(o_operatorOI, DPADButton.POV.LEFT);



  public OI() {
    /* Map d_driverOI buttons/triggers to commands */
    /*this.d_btn_Y.whenPressed(new SetHighGear());
    this.d_btn_A.whenPressed(new SetLowGear()); */
    this.d_btn_X.whenPressed(new SwitchDrivingDirection());
    this.d_btn_B.whenPressed(new DriveToggleRampingUsed());
    this.d_btn_back.whenPressed(new ToggleOperatorControls());
    this.d_btn_start.whenPressed(new GearShift());
    this.driverOperatorEnabled = false;

    /* TODO: (OPTIONAL) Add quick-turn to this.d_btn_LB (left 90 degres) and this.d_btn_RB (right 90 degrees) */
    /* TODO: (OPTIONAL) Toggle break/coast mode for motor contollers */

    /* Map o_operaterOI buttons/triggers to commands */
    /* Wrist-related Commands */
    /* - Simple Manual Wrist */
    this.o_axisBtn_LT.whileHeld(new WristDown(-Constants.kMaxWristSpeed,
                                          Constants.kMinWristPosThreshold,
                                          Constants.kMidWristPosThreshold));
    this.o_axisBtn_LT.whenReleased(new WristSetSpeed(Constants.kRollerHoldPercent));
    this.o_btn_LB.whenPressed(new WristUp(Constants.kMaxWristSpeed,
                                        Constants.kMaxWristPosThreshold,
                                        Constants.kMidWristPosThreshold));
    this.o_btn_LB.whenReleased(new WristSetSpeed(0));
    /* - Smart Automated Wrist (MotionMagic) */
    //this.o_btn_L3.whenPressed(new ToggleWristControl()); // Toggles between Manual and Preset modes
    //this.o_btn_share.whenPressed(new PresentWristControl(Robot.m_wrist.setCargoShipPreset()));
    //this.o_btn_LB.whenPressed(new PresetWristControl(Robot.m_wrist.setUpPreset()));
    //this.o_btn_LT.whenPressed(new PresetWristControl(Robot.m_wrist.setDownPreset()));

    /* Intake-related Commands */
    this.o_btn_back.whenPressed(new ToggleSmartIntake());
    this.o_btn_Y.whileHeld(new IntakeCargo());
    this.o_btn_Y.whenReleased(new StopRollers());
    this.o_btn_A.whileHeld(new EjectCargo());
    this.o_btn_A.whenReleased(new StopRollers());

    /* Hatch-related Commands */
    this.o_btn_X.whenPressed(new ToggleBeak());
    this.o_btn_B.whenPressed(new LaunchHatch());

    /* Elevator related commands */
    /* TODO: Uncomment below commands once Elevator subsystem is implemented */
    //this.o_btn_R3.whenPressed(new PresetElevatorControl(Robot.m_elevator.resetPreset()));
    //this.o_btn_RB.whenPressed(new PresetElevatorControl(Robot.m_elevator.nextPreset()));
    //this.o_btn_RT.whenActive(new PresetElevatorControl(Robot.m_elevator.previousPreset()));
    /* Simple elevator commands */
    this.o_dpad_down.whenPressed(new LiftSetSpeed(-Constants.kMaxLiftSpeed/2)); // Move lift down
    this.o_dpad_down.whenReleased(new LiftSetSpeed(Constants.kLiftHoldSpeed));
    this.o_dpad_up.whenPressed(new LiftSetSpeed(Constants.kMaxLiftSpeed)); // Move lift up
    this.o_dpad_up.whenReleased(new LiftSetSpeed(Constants.kLiftHoldSpeed));
  }

  public void toggleDriverOperatorControls() {
    this.driverOperatorEnabled = !this.driverOperatorEnabled;

    if (this.driverOperatorEnabled) {
      /* copy of operatorOI */
      this.d_axisBtn_LT.whileHeld(new WristDown(-Constants.kMaxWristSpeed,
        Constants.kMinWristPosThreshold,
        Constants.kMidWristPosThreshold));
      this.d_axisBtn_LT.whenReleased(new WristSetSpeed(Constants.kRollerHoldPercent));
      this.d_btn_LB.whenPressed(new WristUp(Constants.kMaxWristSpeed,
        Constants.kMaxWristPosThreshold,
        Constants.kMidWristPosThreshold));
      this.d_btn_LB.whenReleased(new WristSetSpeed(0));
      this.d_dpad_down.whenPressed(new LiftSetSpeed(-Constants.kMaxLiftSpeed/2)); // Move lift down
      this.d_dpad_down.whenReleased(new LiftSetSpeed(Constants.kLiftHoldSpeed));
      this.d_dpad_up.whenPressed(new LiftSetSpeed(Constants.kMaxLiftSpeed)); // Move lift up
      this.d_dpad_up.whenReleased(new LiftSetSpeed(Constants.kLiftHoldSpeed));
      this.d_btn_Y.whileHeld(new IntakeCargo());
      this.d_btn_Y.whenReleased(new StopRollers());
      this.d_btn_A.whileHeld(new EjectCargo());
      this.d_btn_A.whenReleased(new StopRollers());
    } else {
      this.d_axisBtn_LT.whileHeld(new DoNothing());
      this.d_axisBtn_LT.whenReleased(new DoNothing());
      this.d_btn_LB.whenPressed(new DoNothing());
      this.d_btn_LB.whenReleased(new DoNothing());
      this.d_dpad_down.whenPressed(new DoNothing());
      this.d_dpad_down.whenReleased(new DoNothing());
      this.d_dpad_up.whenPressed(new DoNothing());
      this.d_dpad_up.whenReleased(new DoNothing());
      this.d_btn_Y.whileHeld(new DoNothing());
      this.d_btn_Y.whenReleased(new DoNothing());
      this.d_btn_A.whileHeld(new DoNothing());
      this.d_btn_A.whenReleased(new DoNothing());
    }
  }

  /* Used by the DriveTrain subsystem for default command */
  public Xbox getDriverOI() {
    return this.d_driverOI;
  }

  public Xbox getOperatorOI() {
    return this.o_operatorOI;
  }
}
