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

import edu.wpi.first.wpilibj.AnalogInput;
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
  // initializing Talon motor to spin intake
  private TalonSRX m_roller;

  // initializing limit switch to detect when cargo is in place
  // private DigitalInput m_rollerSwitch;

  // initializing IR sensor to detect when cargo is in place
  private AnalogInput m_rollerIR;

  public boolean smartIntake = false;

  public CargoIntake() {
    this.m_roller = new TalonSRX(RobotMap.m_rollerMotor);
    // this.m_rollerSwitch = new DigitalInput(RobotMap.m_cargoRollerLimit);
    this.m_rollerIR = new AnalogInput(RobotMap.m_cargoRollerIR);

    /* TODO: check if motor is reversed */

    /* Set motor to brake when neutral */
    this.m_roller.setNeutralMode(NeutralMode.Brake);

    /* Configure motor to prevent burnout */
    /* TODO: check that these values are correct */
    this.m_roller.configContinuousCurrentLimit(40, 0);
    this.m_roller.configPeakCurrentLimit(60, 0);
    this.m_roller.configPeakCurrentDuration(100, 0);
    this.m_roller.enableCurrentLimit(true);
  }

  public void spinRollersIn() {
    m_roller.set(ControlMode.PercentOutput, Constants.kMaxRollerPercent);
  }

  public void spinRollersOut() {
    m_roller.set(ControlMode.PercentOutput, -Constants.kMaxRollerPercent);
  }

  public void stopRollers() {
    m_roller.set(ControlMode.PercentOutput, 0);
  }

  /* TODO: check when limit switches return true */
  public void spinRollersInWithLimits() {
    if (!isHoldingBall()) {
      spinRollersIn();
    }
  }

  public int getRollerIR() {
    return m_rollerIR.getValue();
  }

  /* TODO: change the constant so it matches a real value */
  public boolean isHoldingBall() {
    return m_rollerIR.getValue() > Constants.kRollerIRThreshold; 
  }

  // public boolean getRollerLimitState() {
  //   return m_rollerSwitch.get();
  // }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
