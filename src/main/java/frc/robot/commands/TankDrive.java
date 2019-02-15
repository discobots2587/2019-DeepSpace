/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.lib.Xbox;

import frc.robot.Robot;

/**
 * DriveTrain command for controlling the robot via tank drive
 */
public class TankDrive extends Command {

  public TankDrive() {
    requires(Robot.m_drive);
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
      Robot.m_drive.rampedTankDrive(driverOI.getLY(),driverOI.getRY());
    } else {
      Robot.m_drive.tankDrive(driverOI.getLY(),driverOI.getRY());
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
    Robot.m_drive.tankDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
