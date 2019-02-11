/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.util.Constants;

public class ArmPID extends PIDSubsystem {

  public Spark m_arm = new Spark(RobotMap.m_arm);
  public AnalogPotentiometer armPoten = new AnalogPotentiometer(RobotMap.m_armPot, Constants.kScaleFactor);

  private double zeroPoint = 0;
  private int index = 0;
  private double outputSpeed = 0; 
  private double targetPosition = 0;

  public ArmPID() {
    
    // Intert a subsystem name and PID values here
    super("ArmPID", Constants.kArmPID_P, Constants.kArmPID_I, Constants.kArmPID_D);
    // Use these to get going:
    // setSetpoint() - Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.

    this.getPIDController().setOutputRange(-0.85, 0.85);
    setAbsoluteTolerance(0.01 * Constants.kScaleFactor);
    m_arm.setInverted(true);
  }

  public void reset() {
    this.zeroPoint = armPoten.get();
    this.setPos(2); 
    this.disable();
  }

  public double getPos() {
    return armPoten.get() - zeroPoint;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setPos(int armPosition) {
    switch(armPosition) {
      case 0: // Down
        this.targetPosition = (zeroPoint + 0.25 * Constants.kScaleFactor); 
        this.setSetpoint(targetPosition);
        break;
      case 1: // switch
        this.targetPosition = (zeroPoint + 0.55 * Constants.kScaleFactor);
        this.setSetpoint(targetPosition);
        break;
      case 2: // to catapult
        this.targetPosition = (zeroPoint + 0.02 * Constants.kScaleFactor);
        this.setSetpoint(targetPosition);
        break;
      default: 
        this.setPos(2);
        break;
    }
    this.index = armPosition; 
  }

  public void raise() {
    setPos(Math.min(++index, 2));
  }

  public void lower() {
    setPos(Math.max(--index, 0));
  }

  public void stop() {
    // empty
  }

  // Return your input value for the PID loop
  @Override
  protected double returnPIDInput() {
    return this.getPos();
  }

  // Use output to drive your system, like a motor
  @Override
  protected void usePIDOutput(double output) {
    this.set(output);
  }

  public void set(double speed) {
    if (speed > Constants.kMaxArmSpeed) {
      speed = Constants.kMaxArmSpeed;
    } else if (speed < -Constants.kMaxArmSpeed) {
      speed = -Constants.kMaxArmSpeed;
    }

    m_arm.set(speed); 
    this.outputSpeed = speed; 
  }

  public int getIndex() {
    return this.index;
  }

  public double getZeroPoint() {
    return this.zeroPoint;
  }

  public double getOutputSpeed() {
    return this.outputSpeed;
  }

  public double getTargetPosition() {
    return this.targetPosition;
  }
}