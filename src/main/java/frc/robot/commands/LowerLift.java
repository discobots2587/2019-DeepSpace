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

public class LowerLift extends Command {
  double motorPowerPercent;
  int minEncoderPosThreshold; // Encoder values increase when lift goes up
  int midEncoderPosThreshold;

  public LowerLift() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_lift);

    this.motorPowerPercent = Constants.kMaxLiftSpeed;
    this.minEncoderPosThreshold = Constants.kMinLiftPosThreshold;
    this.midEncoderPosThreshold = Constants.kMidLiftPosThreshold;
  }

  public LowerLift(double motorPowerPercent) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_lift);

    this.motorPowerPercent = motorPowerPercent;
    this.minEncoderPosThreshold = Constants.kMinLiftPosThreshold;
    this.midEncoderPosThreshold = Constants.kMidLiftPosThreshold;
  }

  public LowerLift(double motorPowerPercent, int minThreshold, int midThreshhold) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_lift);

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
    /* TODO: Check to see if encoder decreases when raising - update if not */
    // Assumes encoder values decrease as lift is raised
    if (Robot.m_lift.getSmartMode() && Robot.m_lift.getPos() < this.minEncoderPosThreshold) {
      Robot.m_lift.stop();
    } else if (Robot.m_lift.getSmartMode() && Robot.m_lift.getPos() < this.midEncoderPosThreshold){
      Robot.m_lift.setMotor(motorPowerPercent / 4);
    } else {
      Robot.m_lift.setMotor(motorPowerPercent);
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
