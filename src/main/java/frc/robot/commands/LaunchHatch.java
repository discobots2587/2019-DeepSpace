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
  private int timeCount;

  public LaunchHatch() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_hatch);
    //this timeCount is incremented by 1 every time excute() is called every 20ms
    this.timeCount = 0;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.m_hatch.launchHatch();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.m_hatch.isHatchLaunched() == true) {
      this.timeCount += 1;
      //multiply by 0.02 second to get a count of actual seconds this command has been running for
      if (this.timeCount * 0.02 > Constants.kHatchDelay){
        Robot.m_hatch.disableLauncher();
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (Robot.m_hatch.isHatchLaunched() == false) {
      return true;
    } else {
      return false;
    }
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
