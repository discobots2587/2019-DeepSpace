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

/**
 * Add your docs here.
 */
public class CargoIntake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // initializing two Talon motors to spin intake and raise intake
  private TalonSRX m_roller;
  private TalonSRX m_arm;

  // initializing two limit switches to detect when cargo is in place
  // and when intake is upright
  private DigitalInput m_rollerSwitch;
  private DigitalInput m_armSwitch;

  public CargoIntake() {
    this.m_roller = new TalonSRX(RobotMap.m_rollerMotor);
    this.m_arm = new TalonSRX(RobotMap.m_armMotor);
    this.m_rollerSwitch = new DigitalInput(RobotMap.m_cargoRollerLimit);
    this.m_armSwitch = new DigitalInput(RobotMap.m_cargoArmLimit);

    /* TODO: check if/which motors are reversed */

    /* Set motors to brake when neutral */
    this.m_arm.setNeutralMode(NeutralMode.Brake);
    this.m_roller.setNeutralMode(NeutralMode.Brake);

    /* Configure motors to prevent burnout */
    /* TODO: check that these values are correct */
    this.m_arm.configContinuousCurrentLimit(40, 0);
    this.m_arm.configPeakCurrentLimit(60, 0);
    this.m_arm.configPeakCurrentDuration(100, 0);
    this.m_arm.enableCurrentLimit(true);
    this.m_roller.configContinuousCurrentLimit(40, 0);
    this.m_roller.configPeakCurrentLimit(60, 0);
    this.m_roller.configPeakCurrentDuration(100, 0);
    this.m_roller.enableCurrentLimit(true);
  }

  public void spinRollersIn() {
    m_roller.set(ControlMode.PercentOutput, 80);
  }

  public void spinRollersOut() {
    m_roller.set(ControlMode.PercentOutput, -80);
  }

  public void stopRollers() {
    m_roller.set(ControlMode.PercentOutput, 0);
  }

  public void spinRollersInWithLimits() {
    if(getRollerLimitState()) {
      spinRollersIn();
    }
  }

  public void moveArm(double speed) {
    m_arm.set(ControlMode.PercentOutput, speed);
  }

  public void moveArmWithLimits(double speed) {
    if(getArmLimitState()) {
      moveArm(speed);
    }
  }

  public boolean getArmLimitState() {
    return m_armSwitch.get();
  }

  public boolean getRollerLimitState() {
    return m_rollerSwitch.get();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
