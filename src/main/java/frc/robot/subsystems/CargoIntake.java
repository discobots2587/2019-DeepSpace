/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
