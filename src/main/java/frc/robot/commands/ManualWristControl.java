/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.lib.Xbox;

public class ManualWristControl extends Command {
  private double pos;

  public ManualWristControl() {
    requires(Robot.m_wrist);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    pos = Robot.m_wrist.getPos();
  }

  // Called repeatedly when this Command is scheduled to run
  /* TODO: possibly tune the conversation factor in deltaPos */
  @Override
  protected void execute() {
    Xbox operatorOI = Robot.m_oi.getOperatorOI();
    double deltaPos = operatorOI.getLY() * 10;

    if(Robot.m_wrist.atBottom() && deltaPos < 0) {
      pos += 0;
    }
    else if(Robot.m_wrist.atTop() && deltaPos > 0) {
      pos += 0;
    } else {
      pos += deltaPos;
    }

    Robot.m_wrist.goTo(pos);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_wrist.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.m_wrist.stop();
  }
}
