/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.lib.Xbox;

import frc.robot.Robot;

public class ArcadeDrive extends Command {
  public ArcadeDrive() {
    requires(Robot.m_drive);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Xbox driverOI = Robot.m_oi.getDriverOI();

    if (Robot.m_drive.getRampingUsed()) {
      Robot.m_drive.rampedArcadeDrive(driverOI.getLY(),driverOI.getRX());
    } else {
      Robot.m_drive.arcadeDrive(driverOI.getLY(),driverOI.getRX());
    }
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
