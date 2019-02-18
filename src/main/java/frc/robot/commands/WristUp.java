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

public class WristUp extends Command {
  double motorPowerPercent;
  double minEncoderPosThreshold; // Encoder values decrease when wrist goes up
  double midEncoderPosThreshold;

  public WristUp() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_wrist);

    this.motorPowerPercent = Constants.kMaxWristSpeed;
    this.minEncoderPosThreshold = Constants.kMinWristPosThreshold;
    this.midEncoderPosThreshold = Constants.kMidWristPosThreshold;
  }

  public WristUp(double motorPowerPercent) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_wrist);

    this.motorPowerPercent = motorPowerPercent;
    this.minEncoderPosThreshold = Constants.kMinWristPosThreshold;
    this.midEncoderPosThreshold = Constants.kMidWristPosThreshold;
  }

  public WristUp(double motorPowerPercent, int minThreshold, int midThreshhold) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_wrist);

    this.motorPowerPercent = motorPowerPercent;
    this.minEncoderPosThreshold = minThreshold;
    this.midEncoderPosThreshold = midThreshhold;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.m_wrist.getPos() < this.minEncoderPosThreshold) {
      Robot.m_wrist.stop();
    } else if (Robot.m_wrist.getPos() < this.midEncoderPosThreshold){
      Robot.m_wrist.setMotor(motorPowerPercent / 4);
    } else {
      Robot.m_wrist.setMotor(motorPowerPercent);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
