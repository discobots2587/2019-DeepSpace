/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Climb extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  DoubleSolenoid m_frontLegs;
  DoubleSolenoid m_backLegs;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public Climb() {
    this.m_frontLegs = new DoubleSolenoid(RobotMap.m_frontLegsExtend,RobotMap.m_frontLegsRetract);
    this.m_backLegs = new DoubleSolenoid(RobotMap.m_backLegsExtend,RobotMap.m_backLegsRetract);
  }

  public void extendFront() {
    this.m_frontLegs.set(Value.kForward);
  }

  public void extendBack() {
    this.m_backLegs.set(Value.kForward);
  }

  public void extendAll() {
    extendFront();
    extendBack();
  }

  public void retractFront() {
    this.m_frontLegs.set(Value.kReverse);
  }

  public void retractBack() {
    this.m_backLegs.set(Value.kReverse);
  }

  public void retractAll() {
    retractFront();
    retractBack();
  }

}
