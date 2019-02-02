/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.RobotMap;
import frc.robot.util.Constants;

/**
 * The CargoIntake has two main functions: to intake cargo and to raise the cargo.
 * There is one TalonSRX motor controlling the roller intake and one controlling the
 * arm itself. There is one limit switch on the roller's hard stop to detect when
 * the cargo has been intaked. There are two limit switches on the arm construction
 * to detect when the arm is in the upright or down positions.
 */
public class CargoIntake extends Subsystem {
  // initializing two Talon motors to spin intake and raise intake
  private TalonSRX m_roller;
  private TalonSRX m_arm;

  // initializing three limit switches to detect when cargo is in place
  // and when intake is upright/down
  private DigitalInput m_rollerSwitch;
  private DigitalInput m_armTopSwitch;
  private DigitalInput m_armBottomSwitch;

  public boolean smartIntake = true;

  enum DeviceID {
    RollerSwitch, ArmTopSwitch, ArmBottomSwitch;
  }

  @SuppressWarnings("serial")
  public static final class InvalidDeviceIdException extends Exception {
    public InvalidDeviceIdException(String message) {
      super(message);
    }
  }

  public CargoIntake() {
    //this.m_roller = new TalonSRX(RobotMap.m_rollerMotor);
    //this.m_arm = new TalonSRX(RobotMap.m_armMotor);
    //this.m_rollerSwitch = new DigitalInput(RobotMap.m_cargoRollerLimit);
    //this.m_armTopSwitch = new DigitalInput(RobotMap.m_cargoArmTopLimit);
    //this.m_armBottomSwitch = new DigitalInput(RobotMap.m_cargoArmBottomLimit);

    /* TODO: check if/which motors are reversed */

    /* Set motors to brake when neutral */
    //this.m_arm.setNeutralMode(NeutralMode.Brake);
    //this.m_roller.setNeutralMode(NeutralMode.Brake);

    /* Configure motors to prevent burnout */
    /* TODO: check that these values are correct */
    //this.m_arm.configContinuousCurrentLimit(40, 0);
    //this.m_arm.configPeakCurrentLimit(60, 0);
    //this.m_arm.configPeakCurrentDuration(100, 0);
    //this.m_arm.enableCurrentLimit(true);
    //this.m_roller.configContinuousCurrentLimit(40, 0);
    //this.m_roller.configPeakCurrentLimit(60, 0);
    //this.m_roller.configPeakCurrentDuration(100, 0);
    //this.m_roller.enableCurrentLimit(true);
  }

  public void spinRollersIn() {
    //m_roller.set(ControlMode.PercentOutput, Constants.kMaxRollerPercent);
  }

  public void spinRollersOut() {
    //m_roller.set(ControlMode.PercentOutput, -Constants.kMaxRollerPercent);
  }

  public void stopRollers() {
    //m_roller.set(ControlMode.PercentOutput, 0);
  }

  /* TODO: check when limit switches return true */
  public void spinRollersInWithLimits() throws InvalidDeviceIdException {
    if (!getLimitState(DeviceID.RollerSwitch)) {
      spinRollersIn();
    }
  }

  public void moveArm(double speed) {
    //m_arm.set(ControlMode.PercentOutput, speed);
  }

  public void moveArmWithLimits(double speed) throws InvalidDeviceIdException {
    boolean atLimit = getLimitState(DeviceID.ArmBottomSwitch) || getLimitState(DeviceID.ArmTopSwitch);
    if(!atLimit) {
      moveArm(speed);
    }
  }

  public boolean getLimitState(DeviceID deviceID) throws InvalidDeviceIdException {
    boolean state = false;
    
    switch(deviceID) {
      case RollerSwitch:
        //state = m_rollerSwitch.get();
        break;
      case ArmTopSwitch:
        //state = m_armTopSwitch.get();
        break;
      case ArmBottomSwitch:
        //state = m_armBottomSwitch.get();
        break;
      default:
        throw new InvalidDeviceIdException("Invalid Device ID");
    }

    return state;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
