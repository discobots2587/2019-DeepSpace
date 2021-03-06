/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.util.Constants;

public class EjectCargo extends Command {
  private double timeCount;

  public EjectCargo() {
    requires(Robot.m_cargoIntake);
    this.timeCount = 0;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.timeCount = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (false && Robot.m_cargoIntake.getSmartIntake()) {
      if (timeCount >= Constants.kCargoEjectTime &&
          timeCount < Constants.kCargoEjectTime + Constants.kCargoEjectSpinBackTime) {
        Robot.m_cargoIntake.spinRollersIn();
      } else {
        Robot.m_cargoIntake.spinRollersOut();
      }
      this.timeCount += 0.02;
    } else {
      Robot.m_cargoIntake.spinRollersOut();
      this.timeCount = 0;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //return !Robot.m_cargoIntake.getSmartIntake();
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.timeCount = 0;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.m_cargoIntake.stopRollers();
    this.timeCount = 0;
  }
}
