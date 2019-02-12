/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.concurrent.TimeUnit;

import javax.swing.SingleSelectionModel;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.RobotMap;
import frc.robot.commands.ExtendBeak;

/**
 * Add your docs here.
 */
public class Hatch extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private DoubleSolenoid m_beak;
  private Solenoid m_launcher;

  /*
   * TODO: confirm with the design team about the type and number of solenoids
   * being used
   * TODO: Verify the CAN id for the PCM and what voltage level is being used
   */

  public Hatch() {
    this.m_launcher = new Solenoid(RobotMap.m_pcm12v, RobotMap.m_launcherExtend);
    this.m_beak = new DoubleSolenoid(RobotMap.m_pcm12v, RobotMap.m_beakExtend, RobotMap.m_beakRetract);
  }

  @Override
  public void initDefaultCommand() {
    // Beak need to be in retracted position first before game.
    setDefaultCommand(new ExtendBeak());
  }

  /* Single Solenoid Helper functions */
  public void pneumaticsOn(Solenoid solenoid) {
    solenoid.set(true);
  }

  public void pneumaticsOff(Solenoid solenoid) {
    solenoid.set(false);
  }

  public void toggleSolenoid(Solenoid solenoid) {
    if (solenoid.get() == true) {
        pneumaticsOff(solenoid);
    } else {
        pneumaticsOn(solenoid);
    }
  }

  /* Double Solenoid Helper functions */
  public void pneumaticsExtend(DoubleSolenoid solenoid) {
    solenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void pneumaticsRetract(DoubleSolenoid solenoid) {
    solenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public void toggleSolenoid(DoubleSolenoid solenoid) {
    if (solenoid.get().equals(DoubleSolenoid.Value.kForward)) {
        pneumaticsRetract(solenoid);
    } else {
        pneumaticsExtend(solenoid);
    }
  }

  /* Beak actions */
  public void toggleBeak() {
    toggleSolenoid(this.m_beak);
  }

  public void extendBeak() {
    pneumaticsExtend(this.m_beak);
  }

  public void retractBeak() {
    pneumaticsRetract(this.m_beak);
  }

  /* Launcher actions */
  public void launchHatch() {
    pneumaticsOn(this.m_launcher);
  }

  public void disableLauncher() {
    pneumaticsOff(this.m_launcher);
  }

  public boolean isHatchLaunched() {
    return this.m_launcher.get();
  }
}
