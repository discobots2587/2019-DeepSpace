/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Spark m_rightIntake;
  private Spark m_leftIntake;

  public Intake(){
    this.m_rightIntake = new Spark(RobotMap.m_rightIntake);
    this.m_leftIntake = new Spark(RobotMap.m_leftIntake);
    this.m_leftIntake.setInverted(true); // Want to have -1 = feed, +1 eject
  }
  
  public void setRightMotorSpeed(double speed){
    m_rightIntake.set(speed);
  }

  public void setLeftMotorSpeed(double speed){
    m_leftIntake.set(speed);
  }

  public void setMotorSpeeds(double leftSpeed, double rightSpeed){
    setLeftMotorSpeed(leftSpeed);
    setRightMotorSpeed(rightSpeed);
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
