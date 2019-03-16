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

public class LaunchHatch extends Command {
  private double timeCount;

  public LaunchHatch() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_hatch);

    //this timeCount is incremented by 0.02 every time excute() is called every 20ms
    this.timeCount = 0.0;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.m_hatch.disableLauncher();
    this.timeCount = 0.0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (this.timeCount == 0.0) {
      Robot.m_hatch.launchHatch();
    }
    this.timeCount += 0.02;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (this.timeCount > Constants.kHatchDelay) {
      return true;
    } else {
      return false;
    }
}

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_hatch.disableLauncher();
    this.timeCount = 0.0;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.m_hatch.disableLauncher();
    this.timeCount = 0.0;
  }
}
