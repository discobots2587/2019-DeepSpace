/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import javax.swing.SingleSelectionModel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.RobotMap;
import frc.robot.commands.BeakSet;

/**
 * Add your docs here.
 */
public class Hatch extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // initiating three pnumatic solenoid that pushes the hatch out
  // using three souble solenoids for now
  private DoubleSolenoid m_beak;
  private DoubleSolenoid m_launcher;

  /*
   * TODO: confirm with the design team about the type and number of solenoids
   * being used
   */

  public Hatch() {
    this.m_launcher = new DoubleSolenoid(RobotMap.m_pcm12v, 0, 1);
    this.m_beak = new DoubleSolenoid(RobotMap.m_pcm12v, 2, 3);
  }

  // simple solenoid operation: push out and turn off immediately
  public void pneumaticsAutoRetract(DoubleSolenoid solenoid){
    solenoid.set(DoubleSolenoid.Value.kForward);
    solenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public void pneumaticsExtend(DoubleSolenoid solenoid){
    solenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void pneumaticsRetract(DoubleSolenoid solenoid){
    solenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public void toggleBeak(){
    if (m_beak.get().equals(DoubleSolenoid.Value.kForward))
      pneumaticsRetract(this.m_beak);
    else
      pneumaticsExtend(this.m_beak);
  }

  public void launchHatch(){
    pneumaticsAutoRetract(this.m_launcher);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.

    //Command toggles beak position. Beak need to be in retracted position first before game.
    setDefaultCommand(new BeakSet()); 
  }
}
