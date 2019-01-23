/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class HatchIntake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // initiating three pnumatic solenoid that pushes the hatch out
  // using three souble solenoids for now
  private DoubleSolenoid m_hatchPusher1; 
  private DoubleSolenoid m_hatchPusher2;
  private DoubleSolenoid m_hatchPusher3;

  /* TODO: confirm with the design team about the type and number of solenoids being used */
  
  public HatchIntake(){
    this.m_hatchPusher1 = new DoubleSolenoid(RobotMap.m_pcm12v, 0, 1);
    this.m_hatchPusher2 = new DoubleSolenoid(RobotMap.m_pcm12v, 2, 3);
    this.m_hatchPusher3 = new DoubleSolenoid(RobotMap.m_pcm12v, 4, 5);
  }

  // simple solenoid operation: push out and turn off immediately
  public void pushOneSol(DoubleSolenoid solenoid){
    solenoid.set(DoubleSolenoid.Value.kForward);
    solenoid.set(DoubleSolenoid.Value.kOff);
  }

  public void pushHatch(){
    pushOneSol(m_hatchPusher1);
    pushOneSol(m_hatchPusher2);
    pushOneSol(m_hatchPusher3);
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
